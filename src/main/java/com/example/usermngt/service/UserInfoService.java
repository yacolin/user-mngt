package com.example.usermngt.service;

import com.example.usermngt.entity.UserInfoEntity;
import com.example.usermngt.model.UserInfo;

import java.util.Map;

public interface UserInfoService {

    Map<String, Object> getAllUserInfos(int page, int size);

    UserInfoEntity getUserInfoById(Long id);

    Boolean update(Long id, UserInfo userInfo);


    UserInfoEntity getUserInfoEntity(Long id);

    UserInfoEntity getUserInfoByUserId(Long userId);

    boolean create(UserInfo userInfo);
}
