package com.myproject.searchingblogs.external_api.kakao.enums;

import java.util.Arrays;

public enum SortBy {
    RECENCY("recency", "최신순"),
    ACCURACY("accuracy", "정확도순");

    private String sort;
    private String description;

    SortBy(String sort, String description) {
        this.sort = sort;
        this.description = description;
    }

    public static void validate(String sort) {
        Arrays.stream(values())
                .filter(s -> s.getSort().equals(sort))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정렬 조건입니다."));
    }

    public String getSort() {
        return sort;
    }
}
