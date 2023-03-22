package com.myproject.searchingblogs.app.domain.search.keyword.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "KEYWORD")
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private long totalCount;

    protected KeywordEntity() {
    }

    public KeywordEntity(String name) {
        this.name = name;
    }
}
