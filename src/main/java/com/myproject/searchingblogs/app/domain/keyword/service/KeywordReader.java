package com.myproject.searchingblogs.app.domain.keyword.service;

import com.myproject.searchingblogs.app.domain.keyword.entity.KeywordEntity;
import com.myproject.searchingblogs.app.domain.keyword.repository.KeywordRepository;
import com.myproject.searchingblogs.app.exception.keyword.KeywordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordReader {

    private final KeywordRepository keywordRepository;

    public List<KeywordEntity> readTop10OrderByTotalCountDesc() {
        return keywordRepository.findTop10ByOrderByTotalCountDesc();
    }

    public KeywordEntity readByName(String name) {
        return keywordRepository.findByName(name)
                .orElseThrow(() -> new KeywordNotFoundException("키워드를 찾을 수 없습니다.", name));
    }
}
