package com.example.usermngt.controller;

import com.example.usermngt.model.User;
import com.example.usermngt.service.Create;
import com.example.usermngt.service.SignService;
import com.example.usermngt.service.Update;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.FormValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/")
public class SignController {
    public final SignService signService;
    public final UserService userService;

    public SignController(SignService signService, UserService userService) {
        this.signService = signService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> Login(@RequestBody @Validated User user) {
        Map<String, Object> token = signService.login(user);
//        Result result = Result.success(token);

        return  ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> Register(@RequestBody @Validated(Create.class) User user, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        boolean flag =  signService.register(user);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/modifyPwd/{id}")
    public ResponseEntity<Boolean> ModifyPwd(@PathVariable("id") Long id, @RequestBody @Validated(Update.class) User user, BindingResult bindingResult) {
        FormValidate.validateField(bindingResult);

        Boolean flag = signService.modifyPwd(id, user);
        return ResponseEntity.ok(flag);
    }
}
