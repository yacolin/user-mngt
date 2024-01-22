package com.example.usermngt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;

    @JsonIgnore
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserInfoEntity userInfoEntity;


    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntities;
}
