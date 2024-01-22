package com.example.usermngt.service.impl;

import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.entity.UserInfoEntity;
import com.example.usermngt.model.UserInfo;
import com.example.usermngt.repository.UserInfoRepository;
import com.example.usermngt.service.UserInfoService;
import com.example.usermngt.service.UserService;
import com.example.usermngt.util.BizException;
import com.example.usermngt.util.PageableList;
import com.example.usermngt.util.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private UserInfoRepository userInfoRepository;
    private UserService userService;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserService userService) {
        this.userInfoRepository = userInfoRepository;
        this.userService = userService;
    }

    @Override
    public Map<String, Object> getAllUserInfos(int page, int size) {
        PageRequest paging = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<UserInfoEntity> userInfoEntityPage = userInfoRepository.findAll(paging);
        Long total = userInfoEntityPage.getTotalElements();

        List<UserInfoEntity> userInfoList = userInfoEntityPage.getContent();
        return PageableList.map(total, userInfoList);
    }

    @Override
    public UserInfoEntity getUserInfoById(Long id) {
        UserInfoEntity userInfoEntity = getUserInfoEntity(id);

        return userInfoEntity;
    }

    @Override
    public UserInfoEntity getUserInfoByUserId(Long userId) {
        UserEntity userEntity = userService.getUserEntity(userId);
        UserInfoEntity userInfoEntity = userInfoRepository.findByUserEntity(userEntity);

        // 数据不存在时，抛出定义的数据不存在异常
        if (userInfoEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }
        return userInfoEntity;
    }

    @Override
    public Boolean update(Long id, UserInfo userInfo) {
        UserInfoEntity userInfoEntity = getUserInfoEntity(id);

        // 获取所有字段
        Field[] fields = UserInfo.class.getDeclaredFields();

        return true;
    }

    @Override
    public boolean create(UserInfo userInfo) {
        // 根据数据中的userId获取userEntity
        UserEntity userEntity = userService.getUserEntity(userInfo.getUserId());

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(userInfo, userInfoEntity);

        // 将userInfoEntity关联到获取到的userEntity上
        userInfoEntity.setUserEntity(userEntity);

        // 保存数据
        userInfoRepository.save(userInfoEntity);
        return true;
    }

    @Override
    public UserInfoEntity getUserInfoEntity(Long id) {
        Optional<UserInfoEntity> OptionalT = userInfoRepository.findById(id);

        // 数据不存在时，抛出定义的数据不存在异常
        UserInfoEntity UserInfoEntity = OptionalT.isPresent()?OptionalT.get() : null;
        if (UserInfoEntity == null) {
            throw new BizException(ResultEnum.DATA_NOT_EXIST);
        }

        return UserInfoEntity;
    }
}
