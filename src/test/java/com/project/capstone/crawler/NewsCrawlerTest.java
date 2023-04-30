package com.project.capstone.crawler;

import com.project.capstone.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsCrawlerTest {


    @InjectMocks
    private NewsCrawler newsCrawler;

    @Mock
    private NewsRepository newsRepository;
    @Test
    void getNewsData() {
        //given
        String url = "https://www.skysports.com/tottenham-hotspur-news";

        newsCrawler.getNewsData(url);
    }

}