package com.example.usermngt.service;

import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface UserService {
    Boolean saveUser(User user);

    UserEntity getUserById(Long id);

    UserEntity getUserByUsername(String username);

    Boolean deleteUser(Long id);

    Boolean updateUser(Long id, User user);

//    List<User> getUserByFirstNameStartWith(String name);

    Map<String, Object> getAllUsers(Specification specification, Pageable pageable);


    UserEntity getUserEntity(Long id);

    Map<String, Object> getAllUsersPost(Long id, Pageable pageable);
}
