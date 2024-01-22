package com.example.usermngt.service.impl;

import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.model.User;
import com.example.usermngt.repository.UserRepository;
import com.example.usermngt.service.SignService;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.BizException;
import com.example.usermngt.util.JasyptPassword;
import com.example.usermngt.util.ResultEnum;
import com.example.usermngt.util.jwttoken.JwtToken;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SignServiceImpl implements SignService {
    private UserRepository userRepository;
    private UserService userService;

    public SignServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public Map<String, Object> login(User user) {
        UserEntity queryUser = userService.getUserByUsername(user.getUsername());

        // 密码校验
        Boolean flag = JasyptPassword.validatePassword(queryUser.getPassword(), user.getPassword());
        if (!flag) {
            throw new BizException(ResultEnum.WRONG_PASSWORD);
        }

        String token = JwtToken.generateToken(user);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        return map;
    }

    @Override
    public Boolean register(User user) {
        String username = user.getUsername();
        UserEntity userEntity = userRepository.findUserByUsername(username);

        if (!(userEntity == null)) {
            throw new BizException(ResultEnum.USER_ALREADY_EXISTS);
        }

        // 保存用户前，对用户密码加密
        String encodedPassword = JasyptPassword.encryptPassword(user.getPassword());
        user.setPassword(encodedPassword);

        return userService.saveUser(user);
    }

    @Override
    public Boolean modifyPwd(Long id, User user) {
        UserEntity userEntity = userService.getUserEntity(id);

        String password = JasyptPassword.encryptPassword(user.getPassword());
        userEntity.setPassword(password);

        userRepository.save(userEntity);
        return true;
    }
}
