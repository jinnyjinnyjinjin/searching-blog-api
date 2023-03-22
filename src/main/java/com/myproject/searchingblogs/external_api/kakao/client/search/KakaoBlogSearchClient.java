package com.myproject.searchingblogs.external_api.kakao.client.search;

import com.myproject.searchingblogs.external_api.common.BlogSearchClient;
import com.myproject.searchingblogs.external_api.common.UriBuilderService;
import com.myproject.searchingblogs.external_api.kakao.client.search.response.KakaoBlogSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoBlogSearchClient implements BlogSearchClient<KakaoBlogSearchResponse> {

    private final RestTemplate restTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private final String PATH = "/search/blog";

    private final UriBuilderService uriBuilderService;

    @Retryable(
            retryFor = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public KakaoBlogSearchResponse search(String query, int page, int size, String sort) {
        URI uri = uriBuilderService.buildURI(PATH, query, page, size, sort);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        log.info("Blog search request uri: {}", uri);

        return restTemplate
                .exchange(uri, HttpMethod.GET, httpEntity, KakaoBlogSearchResponse.class)
                .getBody();
    }

    @Recover
    public KakaoBlogSearchResponse recover(RuntimeException e, String address) {
        log.error("Retries failed. address: {}, error: {}", address, e.getMessage());
        return null;
    }
}
