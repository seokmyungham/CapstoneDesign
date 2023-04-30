package com.project.capstone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String headline;
    private String snippet;
    private String url;
    private LocalDateTime time;
    private String number;

    @Builder
    public News(String image, String headline, String snippet, String url, LocalDateTime time, String number) {
        this.image = image;
        this.headline = headline;
        this.snippet = snippet;
        this.url = url;
        this.time = time;
        this.number = number;
    }
}
