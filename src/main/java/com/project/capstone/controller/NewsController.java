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
        newsCrawler.getNewsData("https://www.skysports.com/tottenham-hotspur-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page));
    }

    @GetMapping("/manchester-united")
    public void NewsList_mu() {
        newsCrawler.getNewsData("https://www.skysports.com/manchester-united");
    }

    @GetMapping("/arsenal")
    public void NewsList_arsenal() {
        newsCrawler.getNewsData("https://www.skysports.com/arsenal");
    }

    @GetMapping("/manchester-city")
    public void NewsList_mc() {
        newsCrawler.getNewsData("https://www.skysports.com/manchester-city");
    }

    @GetMapping("/chelsea")
    public void NewsList_chelsea() {
        newsCrawler.getNewsData("https://www.skysports.com/chelsea");
    }



}
