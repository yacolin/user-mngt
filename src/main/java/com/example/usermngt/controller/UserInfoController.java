package com.example.usermngt.controller;


import com.example.usermngt.entity.UserInfoEntity;
import com.example.usermngt.model.Post;
import com.example.usermngt.model.UserInfo;
import com.example.usermngt.service.Update;
import com.example.usermngt.service.UserInfoService;
import com.example.usermngt.util.FormValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class UserInfoController {
    public final UserInfoService userInfoService;
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/userinfos")
    public ResponseEntity<Map<String, Object>> getAllUserInfos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Map<String, Object> response = userInfoService.getAllUserInfos(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userinfo/{userId}")
    public ResponseEntity<UserInfoEntity> getUserInfo(@PathVariable("userId") Long userId) {
        UserInfoEntity userInfoEntity = userInfoService.getUserInfoByUserId(userId);
        return ResponseEntity.ok(userInfoEntity);
    }

    @PostMapping("/userinfo")
    public ResponseEntity<Boolean> Create(@RequestBody @Validated UserInfo userInfo, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag = userInfoService.create(userInfo);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/userinfo/{id}")
    public ResponseEntity<Boolean> Update(@PathVariable("id") Long id, @RequestBody @Validated UserInfo userInfo, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag = userInfoService.update(id, userInfo);
        return ResponseEntity.ok(flag);
    }

}
