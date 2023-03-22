package com.myproject.searchingblogs.app.service.keyword;

import com.myproject.searchingblogs.app.domain.keyword.service.KeywordFinder;
import com.myproject.searchingblogs.app.domain.keyword.service.dto.KeywordResult;
import com.myproject.searchingblogs.app.service.keyword.dto.KeywordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BlogSearchKeywordService implements KeywordService {
    private final KeywordFinder keywordFinder;

    @Override
    public List<KeywordDto> getTop10OrderByCountDesc() {
        List<KeywordResult> keywords = keywordFinder.findTop10OrderByTotalCountDesc();
        return keywords.stream()
                .map(KeywordDto::of)
                .collect(toList());
    }
}
