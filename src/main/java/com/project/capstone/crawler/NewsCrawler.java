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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NewsCrawler {
    private final NewsRepository newsRepository;

    public void getNewsData(String url) {
        int page = 1;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        try {
            while(true) {
                Document doc = Jsoup.connect(url).data("page", String.valueOf(page)).get();
                Elements newsList = doc.select(".news-list__item");
                if (newsList.isEmpty()) {
                    break; // Latest 뉴스 아이템이 없으면 종료
                }
                for (Element news : newsList) {
                    // 뉴스 게시 날짜 추출
                    String dateString = news.select(".label__timestamp").text();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy h:mma", Locale.ENGLISH);
                    LocalDateTime publishDateTime = LocalDateTime.parse(dateString.toUpperCase(), formatter);

                    if (publishDateTime.getMonthValue() == LocalDate.now().getMonth().getValue() - 1) {
                        return;
                    }

                    String newsId = news.attr("data-id");
                    if (!newsRepository.existsByNumber(newsId)) {

                        String headline = news.select(".news-list__headline").text();
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

                    }

                }
            return;
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
