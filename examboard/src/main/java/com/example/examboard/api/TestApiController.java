package com.example.examboard.api;

import com.example.examboard.dto.TestForm;
import com.example.examboard.entity.TestEntity;
import com.example.examboard.service.TestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestApiController {
    @Autowired
    TestApiService apiService;
    @GetMapping("/api/lists")
    public List<TestEntity> getLists(){
        return apiService.viewList();
    }

    @GetMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> getOneList(@PathVariable Long id){
        return apiService.getOneList(id);
    }

    @PostMapping("/api/list")
    public TestEntity insert(@RequestBody TestForm form){
        return apiService.insertList(form);
    }

    @DeleteMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> delete(@PathVariable Long id){
        return apiService.deleteList(id);
    }

    @PatchMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> patch(@PathVariable Long id,
                                            @RequestBody TestForm form){
        return apiService.patchList(id, form);
    }
}
