package com.myproject.searchingblogs.app.service.search.keyword.dto;

import com.myproject.searchingblogs.app.domain.search.keyword.service.dto.KeywordResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordDto {
    private Long id;
    private String name;
    private long totalCount;

    public static KeywordDto of(KeywordResult result) {
        return KeywordDto.builder()
                .id(result.getId())
                .name(result.getName())
                .totalCount(result.getTotalCount())
                .build();
    }
}
