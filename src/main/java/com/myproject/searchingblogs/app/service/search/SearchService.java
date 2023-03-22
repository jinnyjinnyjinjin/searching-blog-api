package com.myproject.searchingblogs.app.service.search;

import com.myproject.searchingblogs.app.service.dto.SearchedBlogDto;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<SearchedBlogDto> search(String query, int page, int size, String sort);
}
