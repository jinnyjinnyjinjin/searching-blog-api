package com.myproject.searchingblogs.external_api.kakao.service;

import com.myproject.searchingblogs.external_api.common.KakaoConstants;
import com.myproject.searchingblogs.external_api.common.UriBuilderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class KakaoUriBuilderService implements UriBuilderService {

    @Override
    public URI buildURI(String path, String query, int page, int size, String sort) {
        if (!StringUtils.hasText(path)) throw new IllegalArgumentException("path 를 입력하세요.");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(KakaoConstants.KAKAO_BASE_URL);
        return uriBuilder
                .path(path)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)
                .build()
                .encode()
                .toUri();
    }
}
