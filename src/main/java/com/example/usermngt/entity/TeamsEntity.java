package com.example.usermngt.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "teams")
public class TeamsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String city;

    private String logo;

    private int part; // east or west  0 east  1 west

    private String divide; // 赛事分区

    private int champions; // 冠军个数
}
