package com.example.usermngt.service;

import com.example.usermngt.entity.PostEntity;
import com.example.usermngt.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface PostService {
    Map<String, Object> getAllPost(Specification<PostEntity> specification, Pageable pageable);

    boolean create(Post post);

    boolean update(Long id, Post post);

    PostEntity getPostEntity(Long id);
}
