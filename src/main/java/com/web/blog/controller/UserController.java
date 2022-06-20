package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.ArticleDto;
import com.web.blog.dto.UserDto;
import com.web.blog.mapper.CategoryMapper;
import com.web.blog.mapper.LikeLogMapper;
import com.web.blog.pojo.User;
import com.web.blog.pojo.Visitor;
import com.web.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        int result = userService.insertUser(userDto.getUsername(),
                userDto.getPassword(),
                userDto.getNickname(),userDto.getEmail());
        if(result == 2)
            return Result.fail("该用户名已经被注册！");
        return result == 1 ?  Result.succ(200,"注册成功",result):Result.fail("注册失败",result);
    }
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody UserDto userDto) {
        int result = userService.updatePassword(userDto.getId(),userDto.getOld_password(),userDto.getPassword());
        return result == 1 ?  Result.succ("修改密码成功"):Result.fail("修改密码失败！请检查原密码是否正确。",userDto);
    }
    @PostMapping("/editUserInfo")
    public Result editUserInfo(@RequestBody UserDto userDto) {
        int result = userService.updateUserInfo(userDto.getId(),userDto.getNickname(),userDto.getEmail());
        return result == 1 ?  Result.succ(200,"修改用户信息成功！",
                userService.selectUser(userDto.getId())):Result.fail("修改用户信息失败",result);
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
        ArrayList<Object> result = userService.selectAllUser();
        return result.size()!=0 ?  Result.succ(200,"获取用户列表成功！",result):Result.fail("获取用户列表失败",result);
    }

    @PostMapping("/updateVisitor")
    public Result updateVisitor(@RequestBody ArticleDto articleDto) {
        if(articleDto.getUserID() == null)
            return Result.fail("参数错误");
        int result = userService.updateVisitor(articleDto.getUserID());
        return result == 1 ?  Result.succ("更新访客成功！"):Result.fail("更新访客失败",articleDto.getUserID());
    }
    @PostMapping("/getTodayVisitor")
    public Result getVisitor(@RequestBody ArticleDto articleDto) {
        if(articleDto.getUserID() == null)
            return Result.fail("参数错误");
        Object result = articleDto.getUserID() == 1 ?
                userService.selectVisitorNum():userService.selectVisitorNum(articleDto.getUserID());
        return Result.succ(200,"获取访客成功！",result);
    }
    @PostMapping("/getOneWeekVisitor")
    public Result getOneWeekVisitor(@RequestBody ArticleDto articleDto) {
        if(articleDto.getUserID() == null)
            return Result.fail("参数错误");
        //ArrayList<Visitor> result = articleDto.getUserID() == 1 ? userService.selectVisitorNum():userService.selectALLDayUserVisitor(articleDto.getUserID());
        ArrayList<Object> result = articleDto.getUserID() == 1 ?
                userService.selectALLDayUserVisitor() : userService.selectALLDayUserVisitor(articleDto.getUserID());
        return Result.succ(200,"获取访客成功！",result);
    }

}
