package com.web.blog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeLog implements Serializable {
    private Integer userId;
    private Integer articleId;
}
