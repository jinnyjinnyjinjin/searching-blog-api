package com.myproject.searchingblogs.app.service.search;

import com.myproject.searchingblogs.external_api.kakao.enums.SortBy;
import com.myproject.searchingblogs.app.service.search.SearchValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BlogSearchParamValidator implements SearchValidator {

    public void validate(String query, int page, int size, String sort) {
        validateQuery(query);
        validatePage(page);
        validateSize(size);
        validateSort(sort);
    }

    @Override
    public void validateSort(String sort) {
        if (!ObjectUtils.isEmpty(sort)) SortBy.validate(sort);
    }

    @Override
    public void validateSize(int size) {
        if (0 > size || 50 < size)
            throw new IllegalArgumentException("페이지 사이즈는 1 ~ 50 까지만 가능합니다.");
    }

    @Override
    public void validateQuery(String query) {
        if (ObjectUtils.isEmpty(query)) throw new IllegalArgumentException("검색어를 입력하세요.");
    }

    @Override
    public void validatePage(int page) {
        if (0 > page || 50 < page)
            throw new IllegalArgumentException("페이지는 1 ~ 50 까지만 가능합니다.");
    }
}
