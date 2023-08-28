package com.example.examboard.dto;

import com.example.examboard.entity.Article;
import com.example.examboard.entity.UserAccount;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentDto {
    private String content; // 본문
}
