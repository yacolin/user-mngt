package com.example.usermngt.service.impl;

import com.example.usermngt.entity.PostEntity;
import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.model.Post;
import com.example.usermngt.model.PostWithUserId;
import com.example.usermngt.repository.PostRepository;
import com.example.usermngt.service.PostService;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Map<String, Object> getAllPost(Specification<PostEntity> specification, Pageable pageable) {
        Page<PostWithUserId> postEntityPage = postRepository.findAllWithUserId(specification, pageable);
        long total = postEntityPage.getTotalElements();
        List<PostWithUserId> posts = postEntityPage.getContent();

        return  PageableList.map(total, posts);
    }

    @Override
    public boolean create(Post post) {
        // 根据数据中的userId获取userEntity
        UserEntity userEntity = userService.getUserEntity(post.getUserId());

        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(post, postEntity);

        // 将postEntity关联到获取到的userEntity上
        postEntity.setUserEntity(userEntity);

        // 保存数据
        postRepository.save(postEntity);
        return true;
    }

    @Override
    public boolean update(Long id, Post post) {
        PostEntity postEntity = getPostEntity(id);

        postEntity.setContent(post.getContent());

        postRepository.save(postEntity);
        return true;
    }

    @Override
    public PostEntity getPostEntity(Long id) {
        Optional<PostEntity> OptionalT = postRepository.findById(id);
        PostEntity postEntity = OptionalT.isPresent()?OptionalT.get() : null;

        if (postEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }

        return postEntity;
    }
}
