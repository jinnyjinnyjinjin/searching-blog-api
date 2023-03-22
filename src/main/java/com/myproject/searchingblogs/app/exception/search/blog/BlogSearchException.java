package com.myproject.searchingblogs.app.exception.search.blog;

public class BlogSearchException extends RuntimeException {
    private Object field;

    public BlogSearchException(String message, Object field) {
        super(message);
        this.field = field;
    }

    public Object getField() {
        return field;
    }
}
