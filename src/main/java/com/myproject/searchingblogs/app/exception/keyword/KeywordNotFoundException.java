package com.myproject.searchingblogs.app.exception.keyword;

import com.myproject.searchingblogs.app.exception.BlogSearchException;

public class KeywordNotFoundException extends BlogSearchException {

    public KeywordNotFoundException(String message, Object field) {
        super(message, field);
    }
}
