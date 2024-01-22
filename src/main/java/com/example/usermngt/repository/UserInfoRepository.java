package com.example.usermngt.repository;

import com.example.usermngt.entity.UserEntity;
import com.example.usermngt.entity.UserInfoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    UserInfoEntity findByUserEntity(UserEntity userEntity);
}
