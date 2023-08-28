package com.example.examboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Setter
        @ManyToOne
        @JoinColumn(name = "articleId")
        private Article article; // 게시글 (ID)

        @Setter
        @JoinColumn(name = "userId")
        @ManyToOne(optional = false)
        private UserAccount userAccount; // 유저 정보 (ID)

        @Setter
        @Column(nullable = false, length = 500)
        private String content; // 본문
}
