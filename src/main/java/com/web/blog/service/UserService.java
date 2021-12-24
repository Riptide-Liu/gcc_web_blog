package com.web.blog.service;

import com.web.blog.pojo.User;
import com.web.blog.pojo.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

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
            , @Param("nickname") String nickname
            , @Param("email") String email);
    //禁用
    int disableUser(@Param("id") Integer id);
    //启用
    int enableUser(@Param("id") Integer id);
    //获取用户列表
    ArrayList<Object> selectAllUser();
    //获取用户信息
    User selectUser(@Param("id") Integer id);

    //访客量
    int updateVisitor(@Param("userID") Integer userID);
    //查询访客
    Visitor selectVisitor(@Param("userID") Integer userID,
                          @Param("ymd") String ymd);
    //创建访客表
    int insertVisitor(@Param("userID") Integer userID,
                      @Param("ymd") String ymd);

    //查询单用户访客量
    int selectVisitorNum(@Param("userID") Integer userID);
    //查询所有用户访客量
    Object selectVisitorNum();
    //获取一周单用户访问量
    ArrayList<Object> selectALLDayUserVisitor(@Param("userID") Integer userID);
    //获取所有日期访问量
    ArrayList<Object> selectALLDayUserVisitor();
}
