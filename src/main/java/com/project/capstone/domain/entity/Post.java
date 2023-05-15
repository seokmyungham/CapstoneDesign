package com.project.capstone.domain.entity;

import com.project.capstone.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Recommend> recommends = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Post createPost(String title, String content, Member member) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.member = member;

        return post;
    }

    public static Post updatePost(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
        return post;
    }


    private void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("타이틀 사진은 필수 항목입니다.");
        }

        if (!isValidImagePath(title)) {
            throw new IllegalArgumentException("잘못된 이미지 경로입니다.");
        }

        this.title = title;
    }

    private void setContent(String content) {
        if (content.length() > 1000) {
            throw new IllegalArgumentException("게시글 내용은 1000자를 초과할 수 없습니다.");
        }

        this.content = content;
    }

    private boolean isValidImagePath(String imagePath) {
        String extension = imagePath.substring(imagePath.lastIndexOf('.') + 1);
        return extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png");
    }
}
