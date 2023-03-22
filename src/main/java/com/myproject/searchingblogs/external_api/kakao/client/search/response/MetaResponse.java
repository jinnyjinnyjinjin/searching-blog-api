package com.myproject.searchingblogs.external_api.kakao.client.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaResponse {
    @JsonProperty("is_end")
    private boolean isEnd;

    @JsonProperty("pageable_count")
    private long pageableCount;

    @JsonProperty("total_count")
    private long totalCount;
}
