package com.example.usermngt.controller;


import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.entity.UserInfoEntity;
import com.example.usermngt.model.User;
import com.example.usermngt.service.UserInfoService;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.BizException;
import com.example.usermngt.util.Result;
import com.example.usermngt.util.ResultEnum;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class UserController {
    public final UserService userService;
    public final UserInfoService userInfoService;
    public UserController(UserService userService, UserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }


    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email
    ) {
        // 构建分页和查询字段
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Specification<UserEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!id.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (!username.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("username"), username));
            }
            if (!email.equals("")) {
                predicateList.add(criteriaBuilder.equal(root.get("email"), email));
            }

            // 当所有条件都为空时，即无字段查询
            if (predicateList.size() == 0) {
                return null;
            }
            return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
        };

        Map<String, Object> response = userService.getAllUsers(specification, pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.getUserById(id);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        boolean deleted = userService.deleteUser(id);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        boolean flag = userService.updateUser(id, user);
        return ResponseEntity.ok(flag);
    }

    @GetMapping("/users/{id}/post")
    public ResponseEntity<Map<String, Object>> getUserPost(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Map<String, Object> response = userService.getAllUsersPost(id, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}/info")
    public ResponseEntity<UserInfoEntity> getUserInfo(@PathVariable("id") Long id) {
        UserInfoEntity userInfoEntity = userInfoService.getUserInfoByUserId(id);
        return ResponseEntity.ok(userInfoEntity);
    }


    @GetMapping("/users/error")
    public Result error() {
        throw new BizException(ResultEnum.DATA_NOT_EXIST);
    }
}
