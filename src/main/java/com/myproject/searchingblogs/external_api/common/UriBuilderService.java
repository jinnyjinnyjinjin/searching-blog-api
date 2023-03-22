package com.myproject.searchingblogs.external_api.common;

import java.net.URI;

public interface UriBuilderService {
    URI buildURI(String path, String query, int page, int size, String sort);
}
