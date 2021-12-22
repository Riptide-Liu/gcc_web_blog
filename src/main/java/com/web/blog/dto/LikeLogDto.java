package com.web.blog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeLogDto implements Serializable {
    private Integer userID;
    private Integer articleID;
    private Integer likes;
}
