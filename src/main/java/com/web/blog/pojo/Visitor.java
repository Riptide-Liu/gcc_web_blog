package com.web.blog.pojo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {
    private Integer id;
    private Integer userID;
    private Integer visitor;
    private String ymd;
    private Date createTime;
    private Date updateTime;

}
