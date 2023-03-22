package com.myproject.searchingblogs.app.service.dto;

import com.myproject.searchingblogs.external_api.kakao.client.search.response.BlogDocument;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchedBlogDto {

    private String blogName;
    private String title;
    private String contents;
    private String link;
    private String thumbnail;
    private LocalDateTime datetime;

    public static SearchedBlogDto of(BlogDocument blogDocument) {

        LocalDateTime dateTime = null;
        if (!ObjectUtils.isEmpty(blogDocument.getDatetime())) {
            Date date = blogDocument.getDatetime();
            dateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        return SearchedBlogDto.builder()
                .blogName(blogDocument.getBlogName())
                .title(blogDocument.getTitle())
                .contents(blogDocument.getContents())
                .datetime(dateTime)
                .link(blogDocument.getUrl())
                .thumbnail(blogDocument.getThumbnail())
                .build();
    }
}
