package com.web.blog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class likeLog implements Serializable {
    private Integer userId;
    private Integer articleId;
}
