package com.example.usermngt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "user_infos")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private int age;
    private  int gender; // 0 female  1 male
    private String hobbies; // 逗号分隔的字符串
    private String tags;

    // 假设 database_column 是一个存储 1/0 表示布尔值的 tinyint 或 bit 类型的列
    @Column(name = "database_column")
    private boolean flag;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Byte status;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
