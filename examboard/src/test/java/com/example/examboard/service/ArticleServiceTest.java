package com.example.examboard.service;

import com.example.examboard.entity.Article;
import com.example.examboard.repository.ArticleRepository;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("입력테스트")
    void inputTest(){
        for(int i = 1; i<=100; i++){
            Article article = new Article();
            article.setTitle("제목 -- " + i);
            article.setContent("내용입니다.  ~~ " + i);
            articleRepository.save(article);
        }
    }
}