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
    public User selectOne(String username) {
        User user = userMapper.selectOne(username);
        return user;
    }
}
