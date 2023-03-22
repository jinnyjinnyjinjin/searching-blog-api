package com.myproject.searchingblogs.app.service.search.blog;

import com.myproject.searchingblogs.external_api.kakao.client.search.KakaoBlogSearchClient;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.KakaoBlogSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class KakaoBlogSearchClientTest {

    @Autowired
    KakaoBlogSearchClient kakaoBlogSearchClient;

    @Value("${kakao.rest.api.key}")
    String kakaoRestApiKey;

    @Test
    @DisplayName("블로그 검색 API 호출 | 성공")
    void searchBlogs_Success() {
        // given
        String query = "요리";
        int page = 1;
        int size = 20;
        String sort = "";

        // when
        KakaoBlogSearchResponse response = kakaoBlogSearchClient.search(query, page, size, sort);

        // then
        assertNotNull(response);
        assertTrue(response.getDocuments().size() > 1);
        assertFalse(response.getMeta().isEnd());
    }
}