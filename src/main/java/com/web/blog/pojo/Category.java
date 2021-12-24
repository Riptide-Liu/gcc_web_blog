package com.web.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private Integer id;
    private String topic;   //分类名
    private Integer parentId; //父id
    private String parentName; //父名
    private Integer topicLevel;   //层级
    private Integer userId;
}
