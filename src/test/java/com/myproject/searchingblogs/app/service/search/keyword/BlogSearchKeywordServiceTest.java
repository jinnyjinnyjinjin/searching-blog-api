package com.myproject.searchingblogs.app.service.search.keyword;

import com.myproject.searchingblogs.app.service.search.keyword.dto.KeywordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@Sql("/sqls/insert-keywords.sql")
@SpringBootTest
class BlogSearchKeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @Test
    @DisplayName("인기 검색어 내림차순 상단 10개 조회 | 성공")
    void findKeyword_Top10MostSearched_Success() {

        List<KeywordDto> keywords = keywordService.getTop10OrderByCountDesc();

        assertNotNull(keywords);
        assertEquals(10, keywords.size());

        final long biggestCount = keywords.get(0).getTotalCount();

        boolean isBiggest = keywords.stream()
                .noneMatch(keyword -> biggestCount < keyword.getTotalCount());

        assertTrue(isBiggest);
    }
}