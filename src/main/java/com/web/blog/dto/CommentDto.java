package com.web.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Integer id;
    private Integer userID;
    private Integer articleID;
    private String content;
    private Date createTime;
}
