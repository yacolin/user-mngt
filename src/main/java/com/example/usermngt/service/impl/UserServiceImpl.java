package com.example.usermngt.service.impl;

import com.example.usermngt.entity.PostEntity;
import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.entity.UserInfoEntity;
import com.example.usermngt.model.User;
import com.example.usermngt.repository.PostRepository;
import com.example.usermngt.repository.UserInfoRepository;
import com.example.usermngt.repository.UserRepository;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.BizException;
import com.example.usermngt.util.PageableList;
import com.example.usermngt.util.ResultEnum;
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
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private UserInfoRepository userInfoRepository;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Boolean saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Map<String, Object> getAllUsers(Specification specification, Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(specification, pageable);
        long total = userEntityPage.getTotalElements();

        List<UserEntity> users = userEntityPage.getContent();
        return PageableList.map(total, users);
    }

//    @Override
//    public List<User> getUserByFirstNameStartWith(String name) {
//        List<UserEntity> userEntities = userRepository.findUserEntityByFirstNameStartingWith(name);
//
//
//        // 后续可考虑用 modelMapper来优化此写法
////        List<User> users = userEntities
////                .stream()
////                .map(userEntity -> new User(
////                        userEntity.getId(),
////                        userEntity.getFirstName(),
////                        userEntity.getLastName(),
////                        userEntity.getEmailIdIdId()
////                ))
////                .collect(Collectors.toList());
//
//        List<User> users = userEntities.stream().map(userEntity -> {
//            User user = new User();
//            BeanUtils.copyProperties(userEntity, user);
//            return user;
//        }).collect(Collectors.toList());
//
//
//        return users;
//    }


    @Override
    public UserEntity getUserById(Long id) {
        UserEntity userEntity = getUserEntity(id);

        return userEntity;
    }



    @Override
    public Boolean deleteUser(Long id) {
        UserEntity userEntity = getUserEntity(id);
        userRepository.delete(userEntity);
        return true;
    }


    @Override
    public Boolean updateUser(Long id, User user) {
        UserEntity userEntity = getUserEntity(id);

        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());

        userRepository.save(userEntity);
        return true;
    }


    // 该函数为getUserById deleteUser updateUser辅助函数，检测该用户是否存在
    @Override
    public UserEntity getUserEntity(Long id) {
        Optional<UserEntity> OptionalT = userRepository.findById(id);

        // 数据不存在时，抛出定义的数据不存在异常
        UserEntity userEntity = OptionalT.isPresent()?OptionalT.get() : null;
        if (userEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }

        return userEntity;
    }

    @Override
    public Map<String, Object> getAllUsersPost(Long id, Pageable pageable) {
        UserEntity userEntity = getUserEntity(id);
        Page<PostEntity> postEntityPage = postRepository.findByUserEntity(userEntity, pageable);
        long total = postEntityPage.getTotalElements();

        List<PostEntity> posts = postEntityPage.getContent();
        return PageableList.map(total, posts);
    }


    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserByUsername(username);
        if (userEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }

        return userEntity;
    }

}
