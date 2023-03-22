package com.myproject.searchingblogs.app.controller.keyword.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.searchingblogs.app.service.keyword.dto.KeywordDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordResponse {
    private Long id;
    private String name;
    @JsonProperty("total_count")
    private long totalCount;

    public static KeywordResponse of(KeywordDto dto) {
        return KeywordResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .totalCount(dto.getTotalCount())
                .build();
    }
}
