package com.web.blog.service;

import com.web.blog.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    //登录
    User selectOne(@Param("username") String username
            , @Param("password") String password);

    //注册
    int insertUser(@Param("username") String username
            , @Param("password") String password
            , @Param("nickname") String nickname);
    //修改密码
    int updatePassword(@Param("id") Integer id
            , @Param("password") String password);
}
