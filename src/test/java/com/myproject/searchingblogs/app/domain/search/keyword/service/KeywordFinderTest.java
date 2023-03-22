package com.myproject.searchingblogs.app.domain.search.keyword.service;

import com.myproject.searchingblogs.app.domain.search.keyword.entity.KeywordEntity;
import com.myproject.searchingblogs.app.domain.search.keyword.service.dto.KeywordResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeywordFinderTest {

    @Mock
    private KeywordReader keywordReader;

    @InjectMocks
    private KeywordFinder keywordFinder;

    @Test
    @DisplayName("인기 검색어 상위 10개 조회 | 성공")
    void findTop10_Return_Nothing() {
        // given
        List<KeywordEntity> entities = List.of(
                new KeywordEntity("요리"),
                new KeywordEntity("파김치"),
                new KeywordEntity("spring"),
                new KeywordEntity("jpa"),
                new KeywordEntity("슈카월드"),
                new KeywordEntity("참참만쥬"),
                new KeywordEntity("마우스"),
                new KeywordEntity("강아지"),
                new KeywordEntity("맥북"),
                new KeywordEntity("아이폰")
        );

        when(keywordReader.readTop10OrderByTotalCountDesc())
                .thenReturn(entities);

        // when
        List<KeywordResult> keywords = keywordFinder.findTop10OrderByTotalCountDesc();

        // then
        assertEquals(10, keywords.size());
        verify(keywordReader, times(1)).readTop10OrderByTotalCountDesc();
    }
}