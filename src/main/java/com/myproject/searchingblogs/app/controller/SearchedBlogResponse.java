package com.myproject.searchingblogs.app.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.searchingblogs.app.service.dto.SearchedBlogDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SearchedBlogResponse {
    @JsonProperty("blog_name")
    private String blogName;

    private String title;

    private String contents;

    private String link;

    private String thumbnail;

    private LocalDateTime dateTime;

    public static SearchedBlogResponse of(SearchedBlogDto dto) {
        return SearchedBlogResponse.builder()
                .blogName(dto.getBlogName())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .link(dto.getLink())
                .thumbnail(dto.getThumbnail())
                .dateTime(dto.getDatetime())
                .build();
    }
}
