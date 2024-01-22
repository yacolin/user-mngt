package com.example.usermngt.model;

import com.example.usermngt.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class PostWithUserId {
    private Long id;
    private String title;
    private String content;
    private Long liked;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;

    private Long userId;

    // 此构造函数是为了不散列形参，直接在内部调用setter赋值即可
    public PostWithUserId(PostEntity p, Long userId) {
        setId(p.getId());
        setTitle(p.getTitle());
        setContent(p.getContent());
        setLiked(p.getLiked());
        setCreateTime(p.getCreateTime());
        setUpdateTime(p.getUpdateTime());
        setUserId(userId);
    }
}
