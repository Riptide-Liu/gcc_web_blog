package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.CommentDto;
import com.web.blog.pojo.Comment;
import com.web.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
public class CommentController {
    private CommentService commentService;
    @Autowired
    private void setCommentService(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public Result comment(@RequestBody CommentDto commentDto){
        int result = this.commentService.comment(commentDto.getArticleID(), commentDto.getUserID(), commentDto.getContent());
        return (result != 1 ? Result.fail("评论失败") : Result.succ("评论成功"));
    }

    @PostMapping("/recallComment")
    public Result recallComment(@RequestBody CommentDto commentDto){
        int result = this.commentService.recallComment(commentDto.getId());
        return (result != 1 ? Result.fail("删除评论失败") : Result.succ("删除评论成功"));
    }

    @PostMapping("/getCommentsByArticleId")
    public Result getCommentsByArticleId(@RequestBody CommentDto commentDto){
        ArrayList<Map<String, Object>> comments = this.commentService.getCommentsByArticleId(commentDto.getArticleID());
        return  Result.succ(200, "获取文章评论列表成功", comments);
    }

    @PostMapping("/getCommentsByUserId")
    public Result getCommentsByUserId(@RequestBody CommentDto commentDto){
        ArrayList<Map<String, Object>> comments = this.commentService.getCommentsByUserId(commentDto.getUserID());
        return Result.succ(200, "获取用户评论列表成功", comments);
    }

}
