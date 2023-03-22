package com.myproject.searchingblogs.app.controller;

import com.myproject.searchingblogs.app.controller.PageMeta;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageResponse<T> {

    private PageMeta meta;
    private List<T> documents = new ArrayList<>();

    public PageResponse(Page<T> page) {
        Assert.notNull(page, "페이지 값이 없습니다.");
        this.meta = new PageMeta(page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements());
        this.documents.addAll(page.getContent());
    }
}
