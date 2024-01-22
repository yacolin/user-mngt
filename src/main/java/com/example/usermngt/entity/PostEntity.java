package com.example.usermngt.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;

    @Column(name = "liked", columnDefinition = "BIGINT DEFAULT 0")
    private long liked;


    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
