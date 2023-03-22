package com.myproject.searchingblogs.app.exception.search.keyword;

import com.myproject.searchingblogs.app.exception.search.BlogSearchException;

public class KeywordNotFoundException extends BlogSearchException {

    public KeywordNotFoundException(String message, Object field) {
        super(message, field);
    }
}
