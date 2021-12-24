package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.LikeLogDto;
import com.web.blog.mapper.LikeLogMapper;
import com.web.blog.service.LikeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
@Api(tags = "点赞控制模块")
public class LikeLogController {
    private LikeLogService likeLogService;
    @Autowired
    public void setLikeLogService(LikeLogService likeLogService){
        this.likeLogService = likeLogService;
    }

    @PostMapping("/like")
    @ApiOperation(value = "点赞", notes = "需求用户ID与文章ID")
    public Result grantLike(@RequestBody LikeLogDto likeLogDto){
        int result = likeLogService.insertLike(likeLogDto.getUserID(), likeLogDto.getArticleID());
        return (result == 1 ? Result.succ("点赞成功") : Result.fail("点赞失败",likeLogDto));
    }

    @PostMapping("/revokeLike")
    @ApiOperation(value = "取消点赞", notes = "需求用户ID与文章ID")
    public Result revokeLike(@RequestBody LikeLogDto likeLogDto){
        int result = likeLogService.deleteLike(likeLogDto.getUserID(), likeLogDto.getArticleID());
        return (result == 1 ? Result.succ("取消点赞成功") : Result.fail("取消点赞失败"));

    }

    @GetMapping("/Top5Likes")
    public Result Top5Likes() {
        ArrayList<Object> list = likeLogService.Top5CountLike();
        return list != null? Result.succ(200,"获取前五点赞数成功！",list) : Result.fail("获取前五点赞数失败！",list);
    }

//    @PostMapping("/getLikes")
//    @ApiOperation(value = "获取对应文章点赞数", notes = "传入文章ID")
//    public Result getLikes(@RequestBody LikeLogDto likeLogDto){
//        int result = likeLogService.selectLike(likeLogDto.getArticleID());
//        return (result != )
//    }
}
