package com.web.blog.service.impl;

import com.web.blog.mapper.CategoryMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.User;
import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    @Autowired
    public void setDdDataMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectOne(String username, String password) {
        User user = userMapper.selectOne(username,password);
        if(user != null)
            return user.getEnable() == 0 ? null : user;
        else
            return null;
    }

    @Override
    public int insertUser(String username, String password, String nickname, String email) {
        List<User> checkUsername = userMapper.selectUsername(username);
        if(checkUsername.size()>1)
            return 2;

        return userMapper.insertUser(username,password,nickname,email);
    }

    @Override
    public int updatePassword(Integer id,String old_password,String password) {
        int result = 0;
        User check_pwd = userMapper.checkPassword(id,old_password);
        if(check_pwd!=null)
            result = userMapper.updatePassword(id,password);
        return result;
    }

    @Override
    public int updateUserInfo(Integer id, String username, String nickname, String email) {
        int result = userMapper.updateUserInfo(id,username,nickname,email);
        return result;
    }

    @Override
    public int disableUser(Integer id) {
        int result = userMapper.disableUser(id);
        return result;
    }

    @Override
    public int enableUser(Integer id) {
        int result = userMapper.enableUser(id);
        return result;
    }
}
