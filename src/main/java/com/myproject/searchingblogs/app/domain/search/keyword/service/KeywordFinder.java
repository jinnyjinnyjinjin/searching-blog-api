package com.myproject.searchingblogs.app.domain.search.keyword.service;


import com.myproject.searchingblogs.app.domain.search.keyword.entity.KeywordEntity;
import com.myproject.searchingblogs.app.domain.search.keyword.service.dto.KeywordResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class KeywordFinder {

    private final KeywordReader keywordReader;

    public List<KeywordResult> findTop10OrderByTotalCountDesc() {
        List<KeywordEntity> keywords = keywordReader.readTop10OrderByTotalCountDesc();
        return keywords.stream()
                .map(KeywordResult::of)
                .collect(toList());
    }

}
