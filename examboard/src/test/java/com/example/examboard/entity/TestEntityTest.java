package com.example.examboard.entity;

import com.example.examboard.repository.TestEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestEntityTest {
    @Autowired
    TestEntityRepository repository;

    @Test
    void testDataInsert(){
        for(int i=1; i<=10; i++){
            TestEntity entity = new TestEntity();
            entity.setMemo("테스트 " + i);
            repository.save(entity);
        }
    }
}