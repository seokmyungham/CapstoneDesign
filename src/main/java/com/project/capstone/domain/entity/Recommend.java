package com.project.capstone.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;
        post.getRecommends().add(this);
    }

    public static Recommend createRecommend(Member member, Post post) {
        Recommend recommend = new Recommend();
        recommend.member = member;
        recommend.setPost(post);

        return recommend;
    }

    public void removeRecommend(Recommend recommend, Post post) {
        post.getRecommends().remove(recommend);
    }

}
