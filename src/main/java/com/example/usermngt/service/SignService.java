package com.example.usermngt.service;

import com.example.usermngt.model.User;

import java.util.Map;

public interface SignService {
    Map<String, Object> login(User user);

    Boolean register(User user);

    Boolean modifyPwd(Long id, User user);
}
