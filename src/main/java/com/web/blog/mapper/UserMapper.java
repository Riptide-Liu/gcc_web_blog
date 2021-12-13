package com.web.blog.mapper;

import com.web.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //登录
    User selectOne(@Param("username") String username
            , @Param("password") String password);
    //验证用户名唯一
    List<User> selectUsername(@Param("username") String username);
    //获取用户名字
    User selectUsernameAndNickname(@Param("userId") Integer userId);
    //注册
    int insertUser(@Param("username") String username
            , @Param("password") String password
            , @Param("nickname") String nickname
            , @Param("email") String email);
    //修改密码
    int updatePassword(@Param("id") Integer id
            , @Param("password") String password);
    //修改个人信息
    int updateUserInfo(@Param("id") Integer id
            , @Param("username") String username
            , @Param("nickname") String nickname
            , @Param("email") String email);
    //确认密码
    User checkPassword(@Param("id") Integer id
            , @Param("password") String password);
    //禁用
    int disableUser(@Param("id") Integer id);
    //启用
    int enableUser(@Param("id") Integer id);
}
