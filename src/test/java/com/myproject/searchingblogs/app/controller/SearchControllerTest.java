package com.myproject.searchingblogs.app.controller;

import com.myproject.searchingblogs.app.service.dto.SearchedBlogDto;
import com.myproject.searchingblogs.app.service.KakaoBlogSearchService;
import com.myproject.searchingblogs.app.service.keyword.KeywordService;
import com.myproject.searchingblogs.app.service.keyword.mock.MockKeywordService;
import com.myproject.searchingblogs.app.service.keyword.dto.KeywordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KakaoBlogSearchService blogSearchService;

    @MockBean
    private KeywordService keywordService;

    private final String BLOG_PATH = "/api/v1/search/blog";

    private final String KEYWORD_PATH = "/api/v1/search/keywords/most-searched";

    @Test
    @DisplayName("블로그 조회 | sort: 최신순 | 성공")
    void searchBlog_Success() throws Exception {
        String query = "요리";
        int page = 1;
        int size = 10;
        String sort = "recency";
        SearchedBlogDto searchedDto = SearchedBlogDto.builder()
                .title("요리세상")
                .build();
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
        long totalCount = 10;
        Page<SearchedBlogDto> response = new PageImpl<>(List.of(searchedDto), pageable, totalCount);

        when(blogSearchService.search(query, page, size, sort))
                .thenReturn(response);

        // when
        MvcResult mvcResult = this.mockMvc.perform(
                        get(BLOG_PATH)
                                .param("query", query)
                                .param("page", "1")
                                .param("size", "10")
                                .param("sort", "recency")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        verify(blogSearchService, times(1)).search(query, page, size, sort);
    }

    @Test
    @DisplayName("블로그 조회 | query 파라미터 누락 | 400 에러 발생")
    void searchBlog_Fail_400() throws Exception {
        int page = 1;
        int size = 10;
        String sort = "recency";
        String expectedMessage = "검색어를 입력하세요.";

        when(blogSearchService.search(null, page, size, sort))
                .thenThrow(new IllegalArgumentException(expectedMessage));

        MvcResult mvcResult = this.mockMvc.perform(
                        get(BLOG_PATH)
                                .queryParam("page", "1")
                                .queryParam("size", "10")
                                .queryParam("sort", "recency")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof IllegalArgumentException);
                    assertEquals(expectedMessage, result.getResolvedException().getMessage());
                })
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
        verify(blogSearchService, times(1)).search(null, page, size, sort);
    }

    @Test
    @DisplayName("인기 검색어 조회 | 전체 10 개일 때 | 성공")
    void searchKeywords_Success() throws Exception {
        String keyword = "검색어";
        int biggestCount = 10;
        int resultCount = 10;

        MockKeywordService mockKeywordService =
                MockKeywordService.createKeywords(biggestCount, keyword);

        List<KeywordDto> dtoList = mockKeywordService.getTop10OrderByCountDesc();

        when(keywordService.getTop10OrderByCountDesc())
                .thenReturn(dtoList);

        this.mockMvc
                .perform(get(KEYWORD_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(jsonPath("$.data", hasSize(resultCount)))
                .andExpect(jsonPath("$.data[0].id").value(biggestCount))
                .andExpect(jsonPath("$.data[0].name").value(keyword + biggestCount))
                .andExpect(jsonPath("$.data[0].total_count").value(biggestCount))
                .andExpect((response) -> assertEquals(200, response.getResponse().getStatus()))
                .andDo(print());

        verify(keywordService, times(1)).getTop10OrderByCountDesc();
    }

    @Test
    @DisplayName("인기 검색어 조회 | 전체 3 개일 때 | 성공")
    void searchKeywords_Total3_Success() throws Exception {
        String keyword = "검색어";
        int biggestCount = 3;
        int resultCount = 3;

        MockKeywordService mockKeywordService =
                MockKeywordService.createKeywords(biggestCount, keyword);

        List<KeywordDto> dtoList = mockKeywordService.getTop10OrderByCountDesc();

        when(keywordService.getTop10OrderByCountDesc())
                .thenReturn(dtoList);

        this.mockMvc
                .perform(get(KEYWORD_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(jsonPath("$.data", hasSize(resultCount)))
                .andExpect(jsonPath("$.data[0].id").value(biggestCount))
                .andExpect(jsonPath("$.data[0].name").value(keyword + biggestCount))
                .andExpect(jsonPath("$.data[0].total_count").value(biggestCount))
                .andExpect((response) -> assertEquals(200, response.getResponse().getStatus()))
                .andDo(print());

        verify(keywordService, times(1)).getTop10OrderByCountDesc();
    }

    @Test
    @DisplayName("인기 검색어 조회 | 전체가 100 개일 때 | 조회 횟수가 많은 상단 10개만 조회 | 성공")
    void searchKeywords_TotalOver10_ShouldReturn_Top10() throws Exception {
        String keyword = "검색어";
        int biggestCount = 100;
        int resultCount = 10;

        MockKeywordService mockKeywordService =
                MockKeywordService.createKeywords(biggestCount, keyword);

        List<KeywordDto> dtoList = mockKeywordService.getTop10OrderByCountDesc();

        when(keywordService.getTop10OrderByCountDesc())
                .thenReturn(dtoList);

        this.mockMvc
                .perform(get(KEYWORD_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(jsonPath("$.data", hasSize(resultCount)))
                .andExpect(jsonPath("$.data[0].id").value(biggestCount))
                .andExpect(jsonPath("$.data[0].name").value(keyword + biggestCount))
                .andExpect(jsonPath("$.data[0].total_count").value(biggestCount))
                .andExpect((response) -> assertEquals(200, response.getResponse().getStatus()))
                .andDo(print());

        verify(keywordService, times(1)).getTop10OrderByCountDesc();
    }

    @Test
    @DisplayName("블로그 조회 | 서버 에러 발생")
    void searchBlog_ServerError_500() throws Exception {
        String query = "요리";
        int page = 1;
        int size = 10;
        String sort = "recency";

        when(blogSearchService.search(query, page, size, sort))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        MvcResult mvcResult = this.mockMvc.perform(
                        get(BLOG_PATH)
                                .queryParam("query", query)
                                .queryParam("page", "1")
                                .queryParam("size", "10")
                                .queryParam("sort", "recency")
                )
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertEquals(500, mvcResult.getResponse().getStatus());
        verify(blogSearchService, times(1)).search(query, page, size, sort);
    }
}