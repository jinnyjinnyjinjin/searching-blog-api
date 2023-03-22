package com.myproject.searchingblogs.app.controller.search;

import com.myproject.searchingblogs.app.controller.ApiResponse;
import com.myproject.searchingblogs.app.controller.PageResponse;
import com.myproject.searchingblogs.app.controller.search.keyword.response.KeywordResponse;
import com.myproject.searchingblogs.app.service.search.SearchService;
import com.myproject.searchingblogs.app.service.search.keyword.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService blogSearchService;
    private final KeywordService keywordService;

    @GetMapping("/blog")
    public ResponseEntity<PageResponse<SearchedBlogResponse>> search(@RequestParam(required = false) String query,
                                                                 @RequestParam(defaultValue = "1") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size,
                                                                 @RequestParam(defaultValue = "accuracy") String sort) {

        Page<SearchedBlogResponse> responses = blogSearchService.search(query, page, size, sort)
                .map(SearchedBlogResponse::of);
        return new ResponseEntity<>(new PageResponse<>(responses), HttpStatus.OK);
    }

    @GetMapping("/keywords/most-searched")
    public ResponseEntity<ApiResponse> findTop10Keywords() {

        List<KeywordResponse> responses = keywordService.getTop10OrderByCountDesc().stream()
                .map(KeywordResponse::of)
                .toList();
        return new ResponseEntity<>(new ApiResponse(responses), HttpStatus.OK);
    }
}
