package com.myproject.searchingblogs.app.service.search.keyword;

import com.myproject.searchingblogs.app.service.search.keyword.dto.KeywordDto;

import java.util.List;

public interface KeywordService {
    List<KeywordDto> getTop10OrderByCountDesc();
}
