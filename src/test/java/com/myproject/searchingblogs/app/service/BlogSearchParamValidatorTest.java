package com.myproject.searchingblogs.app.service;

import com.myproject.searchingblogs.app.service.BlogSearchParamValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogSearchParamValidatorTest {

    @Mock
    private BlogSearchParamValidator validator;

    @Test
    @DisplayName("검색 조회 시, query 파라미터가 없을 때, 예외 처리")
    void validateQueryParam_ShouldThrows_IllegalArgumentException() {
        // given
        int page = 1;
        int size = 10;
        String sort = "";
        String expectedMessage = "검색어를 입력하세요.";

        doThrow(new IllegalArgumentException(expectedMessage))
                .when(validator)
                .validate(null, 1, 10, sort);

        // when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> validator.validate(null, page, size, sort));

        // then
        verify(validator, timeout(1)).validate(null, page, size, sort);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("검색 조회 시, sort 파라미터가 유효하지 않은 값일 때, 예외 처리")
    void validateSortParam_ShouldThrows_IllegalArgumentException() {
        // given
        int page = 1;
        int size = 10;
        String sort = "nothing";
        String query = "요리";
        String expectedMessage = "정렬 조건이 올바르지 않습니다. recency(최신순) or accuracy(정확도순)";

        doThrow(new IllegalArgumentException("정렬 조건이 올바르지 않습니다. recency(최신순) or accuracy(정확도순)"))
                .when(validator)
                .validate(query, page, size, sort);

        // when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> validator.validate(query, page, size, sort));

        // then
        verify(validator, timeout(1)).validate(query, page, size, sort);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("검색 조회 시, sort 유효함, 파라미터 검증 성공")
    void validateSortParam_Success() {
        // given
        String query = "요리";
        int page = 1;
        int size = 10;
        String sort = "recency";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        doNothing()
                .when(validator)
                .validate(query, page, size, sort);

        // when
        validator.validate(query, page, size, sort);

        // then
        verify(validator, timeout(1)).validate(query, page, size, sort);
    }
}