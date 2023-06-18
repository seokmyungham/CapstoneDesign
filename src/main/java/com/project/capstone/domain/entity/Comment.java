package com.project.capstone.domain.entity;

import com.project.capstone.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comment createComment(String text, Member member, Post post) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setMember(member);
        comment.setPost(post);

        return comment;
    }

    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("댓글 내용이 비어있습니다.");
        }

        if (text.length() > 500) {
            throw new IllegalArgumentException("댓글 내용은 500자를 초과할 수 없습니다.");
        }

        this.text = text;
    }

    public void setPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("게시물은 필수 항목입니다.");
        }

        post.getComments().add(this);
        this.post = post;
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("멤버는 필수 항목입니다.");
        }

        this.member = member;
    }
}
