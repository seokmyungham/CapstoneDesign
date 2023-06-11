package com.project.capstone.controller;

import com.project.capstone.crawler.NewsCrawler;
import com.project.capstone.domain.dto.NewsResponseDto;
import com.project.capstone.domain.entity.Team;
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
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.TH));
    }

    @GetMapping("/manchester-united")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_mu(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/manchester-united-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.MU));
    }

    @GetMapping("/manchester-city")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_mc(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/manchester-city-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.MC));
    }

    @GetMapping("/arsenal")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_as(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/arsenal-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.AS));
    }

    @GetMapping("/liverpool")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_lp(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/liverpool-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.LP));
    }

    @GetMapping("/chelsea")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_ch(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/chelsea-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.CH));
    }


    @GetMapping("/barcelona")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_ba(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/barcelona-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.BA));
    }

    @GetMapping("/real-madrid")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_rm(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/real-madrid-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.RM));
    }

    @GetMapping("/atletico-madrid")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_at(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/atletico-madrid-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.AT));
    }

    @GetMapping("/bayern-munich")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_bm(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/bayern-munich-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.BM));
    }

    @GetMapping("/borussia-dortmund")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_dm(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/borussia-dortmund-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.DM));
    }

    @GetMapping("/milan")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_ac(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/ac-milan-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.AC));
    }

    @GetMapping("/inter")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_in(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/inter-milan-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.IN));
    }

    @GetMapping("/juventus")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_ju(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/juventus-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.JU));
    }

    @GetMapping("/napoli")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_na(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/napoli-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.NA));
    }

    @GetMapping("/paris")
    public ResponseEntity<Page<NewsResponseDto>> NewsList_ps(@RequestParam(name = "page") int page) {
        newsCrawler.getNewsData_SkySports("https://www.skysports.com/paris-st-germain-news");
        return ResponseEntity.ok(newsCrawler.pageNews(page, Team.PS));
    }


}
