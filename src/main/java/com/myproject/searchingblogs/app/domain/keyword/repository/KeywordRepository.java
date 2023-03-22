package com.myproject.searchingblogs.app.domain.keyword.repository;

import com.myproject.searchingblogs.app.domain.keyword.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
    List<KeywordEntity> findTop10ByOrderByTotalCountDesc();
    Optional<KeywordEntity> findByName(String name);
}
