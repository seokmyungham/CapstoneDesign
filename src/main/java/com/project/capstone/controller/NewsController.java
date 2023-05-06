package com.project.capstone.controller;

import com.project.capstone.crawler.NewsCrawler;
import com.project.capstone.domain.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsCrawler newsCrawler;

    @GetMapping("/tottenham-hotspur")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_th(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/tottenham-hotspur-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page));
    }
}
