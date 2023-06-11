package com.project.capstone.crawler;

import com.project.capstone.domain.dto.NewsResponseDto;
import com.project.capstone.domain.entity.News;
import com.project.capstone.domain.entity.Team;
import com.project.capstone.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NewsCrawler {
    private final NewsRepository newsRepository;

    public void getNewsData_SkySports(String url) {
        int page = 1;
        int count = 0;
        Team team = null;

        if (url.contains("manchester-united-news")) {
            team = Team.MU;
        } else if (url.contains("tottenham-hotspur-news")) {
            team = Team.TH;
        } else if (url.contains("manchester-city-news")) {
            team = Team.MC;
        } else if (url.contains("arsenal")) {
            team = Team.AS;
        } else if (url.contains("liverpool")) {
            team = Team.LP;
        } else if (url.contains("chelsea")) {
            team = Team.CH;
        } else if (url.contains("barcelona")) {
            team = Team.BA;
        } else if (url.contains("real-madrid")) {
            team = Team.RM;
        } else if (url.contains("atletico-madrid")) {
            team = Team.AT;
        } else if (url.contains("bayern-munich")) {
            team = Team.BM;
        } else if (url.contains("borussia-dortmund")) {
            team = Team.DM;
        } else if (url.contains("ac-milan")) {
            team = Team.AC;
        } else if (url.contains("inter-milan")) {
            team = Team.IN;
        } else if (url.contains("juventus")) {
            team = Team.JU;
        } else if (url.contains("napoli")) {
            team = Team.NA;
        } else if (url.contains("paris")) {
            team = Team.PS;
        }


        try {
            Document doc = Jsoup.connect(url).data("page", String.valueOf(page)).get();
            Elements newsList = doc.select(".news-list__item");

            List<Element> reversedNewsList = new ArrayList<>(newsList);
            Collections.reverse(reversedNewsList);

            for (Element news : reversedNewsList) {
                // 뉴스 게시 날짜 추출
                String dateString = news.select(".label__timestamp").text();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy h:mma", Locale.ENGLISH);
                LocalDateTime publishDateTime = LocalDateTime.parse(dateString.toUpperCase(), formatter);

                String newsId = news.attr("data-id");
                String headline = news.select(".news-list__headline").text();

                if (!newsRepository.existsByHeadlineAndTeam(headline, team)) {
                    String snippet = news.select(".news-list__snippet").text();
                    String imageUrl = news.select(".news-list__image").attr("data-src");
                    String link = news.select(".news-list__headline a").attr("href");

                    News news1 = News.builder()
                            .image(imageUrl)
                            .headline(headline)
                            .snippet(snippet)
                            .url(link)
                            .time(publishDateTime)
                            .number(newsId)
                            .team(team)
                            .build();

                    log.info("새로운 뉴스입니다.");
                    newsRepository.save(news1);
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<NewsResponseDto> pageNews(int pageNum, Team team) {
        return newsRepository.searchAllNews(team, PageRequest.of(pageNum - 1, 20));
    }

}
