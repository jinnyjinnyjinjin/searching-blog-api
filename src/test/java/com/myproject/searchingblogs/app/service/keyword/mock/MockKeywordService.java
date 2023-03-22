package com.myproject.searchingblogs.app.service.keyword.mock;

import com.myproject.searchingblogs.app.service.keyword.KeywordService;
import com.myproject.searchingblogs.app.service.keyword.dto.KeywordDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MockKeywordService implements KeywordService {

    private List<KeywordDto> totalList;

    public static MockKeywordService createKeywords(int size, String keyword) {
        MockKeywordService service = new MockKeywordService();
        service.totalList = IntStream.range(1, size + 1)
                .mapToObj(i -> KeywordDto.builder()
                        .id((long) i)
                        .name(keyword + i)
                        .totalCount(i)
                        .build())
                .collect(toList());
        return service;
    }

    @Override
    public List<KeywordDto> getTop10OrderByCountDesc() {
        return this.totalList.stream()
                .sorted(Comparator.comparing(KeywordDto::getTotalCount).reversed())
                .limit(10)
                .collect(toList());
    }
}
