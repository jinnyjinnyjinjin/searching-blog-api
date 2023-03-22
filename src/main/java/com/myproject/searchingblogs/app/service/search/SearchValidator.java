package com.myproject.searchingblogs.app.service.search;

public interface SearchValidator {
    void validateQuery(String query);
    void validatePage(int page);
    void validateSize(int size);
    void validateSort(String sort);
}
