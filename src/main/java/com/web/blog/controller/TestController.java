package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.User;
import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/first")
    public String askForLeave() {
        return "第一个接口";
    }

    @GetMapping("/selectOne")
    public Result selectOneUser() {
        User user = userService.selectOne("lhd");
        System.out.println(user);
        if(user != null)
            return Result.succ(200,"查询成功",user);
        else
            return Result.fail("查询失败");
    }
}
