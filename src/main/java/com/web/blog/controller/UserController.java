package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.UserDto;
import com.web.blog.mapper.CategoryMapper;
import com.web.blog.pojo.User;
import com.web.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin //所有域名均可访问该类下所有接口
@Api(tags = "用户管理模块")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "接收用户名和密码两个参数")
    public Result selectOneUser(@RequestBody UserDto userDto) {
        User user = userService.selectOne(userDto.getUsername(),userDto.getPassword());
        if(user != null)
            return Result.succ(200,"登录成功",user);
        else
            return Result.fail("登录失败!","密码错误或账号已被封禁！");
    }
    @PostMapping("/register")
    public Result userRegister(@RequestBody UserDto userDto) {
        int result = userService.insertUser(userDto.getUsername(),userDto.getPassword(),userDto.getNickname(),userDto.getEmail());
        if(result == 2)
            return Result.fail("该用户名已经被注册！");
        return result == 1 ?  Result.succ(200,"注册成功",result):Result.fail("注册失败",result);
    }
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody UserDto userDto) {
        int result = userService.updatePassword(userDto.getId(),userDto.getOld_password(),userDto.getPassword());
        return result == 1 ?  Result.succ("修改密码成功"):Result.fail("修改密码失败",result);
    }
    @PostMapping("/editUserInfo")
    public Result editUserInfo(@RequestBody UserDto userDto) {
        int result = userService.updateUserInfo(userDto.getId(),userDto.getUsername(),userDto.getNickname(),userDto.getEmail());
        return result == 1 ?  Result.succ("修改用户信息成功！"):Result.fail("修改用户信息失败",result);
    }
    @PostMapping("/disableUser")
    public Result disableUser(@RequestBody UserDto userDto) {
        int result = userService.disableUser(userDto.getId());
        return result == 1 ?  Result.succ("封禁用户成功！"):Result.fail("封禁用户失败",result);
    }
    @PostMapping("/enableUser")
    public Result enableUser(@RequestBody UserDto userDto) {
        int result = userService.enableUser(userDto.getId());
        return result == 1 ?  Result.succ("解封用户成功！"):Result.fail("解封用户失败",result);
    }
    @GetMapping("/userList")
    public Result userList() {
        List<User> result = userService.selectAllUser();
        return result.size()!=0 ?  Result.succ(200,"获取用户列表成功！",result):Result.fail("获取用户列表失败",result);
    }
}
