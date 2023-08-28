package com.example.examboard.entity;

import com.example.examboard.dto.ArticleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article  extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articleId")
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false)
    private String title; // 제목
    @Column(nullable = false, length = 10000)
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> articleCommentList = new ArrayList<>();
}
