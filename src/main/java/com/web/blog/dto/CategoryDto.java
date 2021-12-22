package com.web.blog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDto implements Serializable {
    private Integer id;
    private String topic;   //分类名
    private Integer parentId; //父id
    private Integer topicLevel;   //层级

}
