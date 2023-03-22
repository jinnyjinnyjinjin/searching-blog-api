package com.myproject.searchingblogs.app.service.search.blog;

import com.myproject.searchingblogs.app.service.search.BlogSearchParamValidator;
import com.myproject.searchingblogs.app.service.search.KakaoBlogSearchService;
import com.myproject.searchingblogs.external_api.common.BlogSearchClient;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.MetaResponse;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.BlogDocument;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.KakaoBlogSearchResponse;
import com.myproject.searchingblogs.app.service.dto.SearchedBlogDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KakaoBlogSearchServiceTest {

    @Mock
    private BlogSearchClient<KakaoBlogSearchResponse> blogSearchClient;

    @InjectMocks
    private KakaoBlogSearchService kakaoBlogSearchService;

    @Mock
    private BlogSearchParamValidator blogSearchParamValidator;

    @Test
    @DisplayName("블로그 검색 client 호출 - | page: 1 | totalCount: 10 | totalPages: 2 | 성공")
    void callKakaoBlogSearchClient_Success() {
        // given
        String query = "요리";
        int page = 1;
        int size = 10;
        String sort = "";

        MetaResponse meta = MetaResponse.builder()
                .pageableCount(10)
                .build();
        BlogDocument blogDocument = BlogDocument.builder()
                .blogName("나의 블로그")
                .datetime(new Date())
                .title("난중일기")
                .build();
        KakaoBlogSearchResponse response = KakaoBlogSearchResponse.builder()
                .meta(meta)
                .documents(List.of(blogDocument))
                .build();

        doNothing()
                .when(blogSearchParamValidator).validate(query, page, size, sort);

        when(blogSearchClient.search(anyString(), anyInt(), anyInt(), anyString()))
                .thenReturn(response);

        // when
        Page<SearchedBlogDto> result = kakaoBlogSearchService.search(query, page, size, sort);

        // then
        assertEquals(11, result.getTotalElements());
        assertEquals(1, result.getNumber());
        assertEquals(2, result.getTotalPages());
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("블로그 검색 client 호출 - | 클라이언트 반환 데이터가 Null | 성공")
    void callKakaoBlogSearchClient_Response_Null() {
        // given
        String query = "요리";
        int page = 1;
        int size = 5;
        String sort = "";

        doNothing()
                .when(blogSearchParamValidator).validate(query, page, size, sort);

        when(blogSearchClient.search(anyString(), anyInt(), anyInt(), anyString()))
                .thenReturn(null);

        // when
        Page<SearchedBlogDto> result = kakaoBlogSearchService.search(query, page, size, sort);

        // then
        assertEquals(0, result.getContent().size());
    }
}