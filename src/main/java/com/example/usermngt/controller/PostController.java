package com.example.usermngt.controller;


import com.example.usermngt.entity.PostEntity;
import com.example.usermngt.model.Post;
import com.example.usermngt.service.Create;
import com.example.usermngt.service.PostService;
import com.example.usermngt.service.Update;
import com.example.usermngt.util.FormValidate;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class PostController {
    public final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getAllPost(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String id
    ) {
        // 构建分页和查询字段
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Specification<PostEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!id.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), id));
            }
            // 当所有条件都为空时，即无字段查询
            if (predicateList.size() == 0) {
                return null;
            }
            return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        Map<String, Object> response = postService.getAllPost(specification, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/post")
    public ResponseEntity<Boolean> Create(@RequestBody @Validated(Create.class) Post post, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag = postService.create(post);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Boolean> Update(@PathVariable("id") Long id, @RequestBody @Validated(Update.class) Post post, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag = postService.update(id, post);
        return ResponseEntity.ok(flag);
    }

}
