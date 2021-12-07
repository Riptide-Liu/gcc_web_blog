package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.UserDto;
import com.web.blog.mapper.CategoryMapper;
import com.web.blog.pojo.User;
import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin //所有域名均可访问该类下所有接口
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result selectOneUser(@RequestParam(value = "username", required = true) String username
                                ,@RequestParam(value = "password", required = true) String password) {
        User user = userService.selectOne(username,password);
        if(user != null)
            return Result.succ(200,"登录成功",user);
        else
            return Result.fail("登录失败!","密码错误或账号已被封禁！");
    }
    @PostMapping("/register")
    public Result userRegister(@RequestParam(value = "username", required = true) String username
            ,@RequestParam(value = "password", required = true) String password
            ,@RequestParam(value = "nickname", required = false) String nickname) {
        int result = userService.insertUser(username,password,nickname);
        return result == 1 ?  Result.succ(200,"注册成功",result):Result.fail("注册失败",result);
    }
    @PostMapping("/changePassword")
    public Result changePassword(@RequestParam(value = "id", required = true) Integer id
            ,@RequestParam(value = "password", required = true) String password) {
        int result = userService.updatePassword(id,password);
        return result == 1 ?  Result.succ("修改密码成功"):Result.fail("修改密码失败");
    }
}
