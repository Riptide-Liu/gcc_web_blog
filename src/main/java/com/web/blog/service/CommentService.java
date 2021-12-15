package com.web.blog.service;

import com.web.blog.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

public interface CommentService {

    // 添加评论
    int comment(@Param("articleId") Integer articleId,
                @Param("userId") Integer userId,
                @Param("content") String content);

    // 删除评论
    int recallComment(@Param("id") Integer id);

    // 获取对应文章的所有评论
    ArrayList<Map<String, Object>> getCommentsByArticleId(@Param("articleId") Integer articleId);

    // 获取对应用户的所有评论
    ArrayList<Map<String, Object>> getCommentsByUserId(@Param("userId") Integer userId);
}
