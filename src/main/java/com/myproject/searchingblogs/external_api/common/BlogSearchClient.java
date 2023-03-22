package com.myproject.searchingblogs.external_api.common;

public interface BlogSearchClient<T> {
    T search(String query, int page, int size, String sort);
}
