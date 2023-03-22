package com.myproject.searchingblogs.app.domain.search.keyword.service.dto;

import com.myproject.searchingblogs.app.domain.search.keyword.entity.KeywordEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordResult {
    private Long id;
    private String name;
    private long totalCount;

    public static KeywordResult of(KeywordEntity entity) {
        return KeywordResult.builder()
                .id(entity.getId())
                .name(entity.getName())
                .totalCount(entity.getTotalCount())
                .build();
    }
}
