package com.myproject.searchingblogs.external_api.kakao.client.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDocument {

    @JsonProperty("blogname")
    private String blogName;
    private String contents;
    private Date datetime;
    private String thumbnail;
    private String title;
    private String url;
}
