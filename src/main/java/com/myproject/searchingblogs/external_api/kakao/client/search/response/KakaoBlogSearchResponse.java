package com.myproject.searchingblogs.external_api.kakao.client.search.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoBlogSearchResponse {
    private MetaResponse meta;
    @Builder.Default
    private List<BlogDocument> documents = new ArrayList<>();
}
