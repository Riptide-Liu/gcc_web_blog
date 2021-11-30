package com.web.blog.service;

import com.web.blog.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    User selectOne(@Param("username") String username);
}
