package com.example.usermngt.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teams")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String city;

    private String logo;

    @Column(columnDefinition = "char(1)")
    private Character part; // e or w

    private String divide; // 赛事分区

    @Column(columnDefinition = "TINYINT(0) DEFAULT 0")
    private int champions; // 冠军个数


    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;
}
