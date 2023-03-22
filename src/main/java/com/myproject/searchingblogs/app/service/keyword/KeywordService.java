package com.myproject.searchingblogs.app.service.keyword;

import com.myproject.searchingblogs.app.service.keyword.dto.KeywordDto;

import java.util.List;

public interface KeywordService {
    List<KeywordDto> getTop10OrderByCountDesc();
}
