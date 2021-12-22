package com.web.blog.mapper;

import com.web.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Mapper
@Repository
public interface CommentMapper {
    // 创建评论记录
    int insertComment(@Param("articleId") Integer articleId,
                      @Param("userId") Integer userId,
                      @Param("content") String content);

    // 删除评论记录
    int deleteComment(@Param("id") Integer id);

    // 获取对应文章评论集
    ArrayList<Comment> selectCommentsByArticleId(@Param("articleId") Integer articleId);

    // 获取对应用户评论列表
    ArrayList<Comment> selectCommentsByUserId(@Param("userId") Integer userId);
}
