package com.project.capstone.crawler;

import com.project.capstone.domain.dto.NewsResponseDto;
import com.project.capstone.domain.entity.News;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NewsCrawler {
    private final NewsRepository newsRepository;

    public void getNewsData_SkySports(String url) {
        int page = 1;
        int count = 0;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
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

                if (!newsRepository.existsByHeadline(headline)) {
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

    public void getNewsData_Naver(String url, String keyword1, String keyword2) {
        int count = 0;
        try {
            while (count < 20) {
                Document doc = Jsoup.connect(url).get();
                Elements newsList = doc.select(".news_list");

                if (newsList.isEmpty()) {
                    System.out.println("종료다");
                    break; // Latest 뉴스 아이템이 없으면 종료
                }
                for (Element news : newsList) {
                    // 뉴스 게시 날짜 추출
//                    String dateString = news.select(".info").text();
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
//                    LocalDateTime publishDateTime = LocalDateTime.parse(dateString, formatter);

                    String headline = news.select(".title").text();
                    System.out.println(headline);
                    System.out.println("ㅎㅇㅎㅇ");
//                    if (headline.contains(keyword1) || headline.contains(keyword2)) { // 헤드라인에 keyword가 포함된 경우에만 처리
//
//                        if (!newsRepository.existsByHeadline(headline)) {
//                            String link = news.select(".title a").attr("href");
//                            String imageUrl = news.select(".photo img").attr("src");
//                            String snippet = news.select(".desc").text();
//                            log.info("새로운 뉴스입니다.");
//                            News news1 = News.builder()
//                                    .image(imageUrl)
//                                    .headline(headline)
//                                    .snippet(snippet)
//                                    .url(link)
//                                    .time(null)
//                                    .build();
//                            newsRepository.save(news1);
                            count++;
//                        }
//                    }
//
                    if (count == 20) { // count가 20이 되면 loop를 종료합니다.
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<NewsResponseDto> newsResponse() {
        List<News> newsList = newsRepository.findAll();
        return newsList
                .stream()
                .map(NewsResponseDto::of)
                .collect(Collectors.toList());
    }

    public Page<NewsResponseDto> pageNews(int pageNum) {
        return newsRepository.searchAllNews(PageRequest.of(pageNum - 1, 20));
    }

}
