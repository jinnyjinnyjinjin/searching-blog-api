package com.myproject.searchingblogs.app.service.search;

import com.myproject.searchingblogs.external_api.common.BlogSearchClient;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.KakaoBlogSearchResponse;
import com.myproject.searchingblogs.app.service.dto.SearchedBlogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoBlogSearchService implements SearchService {

    private final BlogSearchClient<KakaoBlogSearchResponse> blogSearchClient;
    private final BlogSearchParamValidator blogSearchParamValidator;

    public Page<SearchedBlogDto> search(String query, int page, int size, String sort) {

        blogSearchParamValidator.validate(query, page, size, sort);

        KakaoBlogSearchResponse response = blogSearchClient.search(query, page, size, sort);

        if (ObjectUtils.isEmpty(response))
            response = new KakaoBlogSearchResponse();

        List<SearchedBlogDto> searchedBlogDtos = response.getDocuments().stream()
                .map(SearchedBlogDto::of)
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        long totalCount = ObjectUtils.isEmpty(response.getMeta()) ? 0 : response.getMeta().getTotalCount();
        return new PageImpl<>(searchedBlogDtos, pageable, totalCount);
    }
}
