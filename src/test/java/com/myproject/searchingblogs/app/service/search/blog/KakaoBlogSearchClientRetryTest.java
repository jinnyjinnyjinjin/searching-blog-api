package com.myproject.searchingblogs.app.service.search.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.searchingblogs.external_api.kakao.client.search.KakaoBlogSearchClient;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.MetaResponse;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.BlogDocument;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.KakaoBlogSearchResponse;
import com.myproject.searchingblogs.external_api.kakao.service.KakaoUriBuilderService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class KakaoBlogSearchClientRetryTest {

    private MockWebServer mockWebServer;

    @MockBean
    KakaoUriBuilderService kakaoUriBuilderService;

    @Autowired
    private KakaoBlogSearchClient kakaoBlogSearchClient;

    @Value("${kakao.rest.api.key}")
    String kakaoRestApiKey;

    private final String path = "/search/blog";

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("블로그 검색 api 호출 retry - 성공")
    void requestBlogSearchRetry_Success() throws JsonProcessingException, InterruptedException {
        // given
        String query = "요리";
        int page = 1;
        int size = 20;
        String sort = "";
        String blogName = "난중일기";
        MetaResponse metaResponse = MetaResponse.builder()
                .totalCount(1)
                .build();
        BlogDocument blogDocument = BlogDocument.builder()
                .blogName(blogName)
                .build();
        KakaoBlogSearchResponse expectedResponse = new KakaoBlogSearchResponse(metaResponse, List.of(blogDocument));
        URI uri = mockWebServer.url("/").uri();

        when(kakaoUriBuilderService.buildURI(path, query, page, size, sort))
                .thenReturn(uri);

        //when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectedResponse))
        );
        KakaoBlogSearchResponse result = kakaoBlogSearchClient.search(query, page, size, sort);
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        // then
        verify(kakaoUriBuilderService, times(2))
                .buildURI(anyString(), anyString(), anyInt(), anyInt(), anyString());
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(1, result.getDocuments().size());
        assertEquals(1, result.getMeta().getTotalCount());
        assertEquals(result.getDocuments().get(0).getBlogName(), blogName);
    }

    @Test
    @DisplayName("블로그 검색 api 호출 retry - 실패")
    void requestBlogSearchRetry_Fail() {
        // given
        String query = "요리";
        int page = 1;
        int size = 20;
        String sort = "";
        URI uri = mockWebServer.url("/").uri();

        // when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));

        when(kakaoUriBuilderService.buildURI(path, query, page, size, sort))
                .thenReturn(uri);

        KakaoBlogSearchResponse result = kakaoBlogSearchClient.search(query, page, size, sort);

        // then
        verify(kakaoUriBuilderService, times(2))
                .buildURI(anyString(), anyString(), anyInt(), anyInt(), anyString());
        assertNull(result);
    }
}
