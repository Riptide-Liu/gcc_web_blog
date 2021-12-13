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
            , @Param("nickname") String nickname
            , @Param("email") String email);
    //修改密码
    int updatePassword(@Param("id") Integer id
            , @Param("old_password") String old_password
            , @Param("password") String password);
    //修改个人信息
    int updateUserInfo(@Param("id") Integer id
            , @Param("username") String username
            , @Param("nickname") String nickname
            , @Param("email") String email);
    //禁用
    int disableUser(@Param("id") Integer id);
    //启用
    int enableUser(@Param("id") Integer id);
}
