package com.web.blog.service.impl;

import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.User;
import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectOne(String username, String password) {
        User user = userMapper.selectOne(username,password);
        return user;
    }

    @Override
    public int insertUser(String username, String password, String nickname) {
        int result = userMapper.insertUser(username,password,nickname);
        return result;
    }

    @Override
    public int updatePassword(Integer id, String password) {
        int result = userMapper.updatePassword(id,password);
        return result;
    }
}
