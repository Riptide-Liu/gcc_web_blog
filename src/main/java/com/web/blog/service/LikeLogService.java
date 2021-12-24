package com.web.blog.service;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface LikeLogService {
    //点赞
    int insertLike(@Param("userId") Integer userId,
                   @Param("articleId") Integer articleId);

    //取消点赞
    int deleteLike(@Param("userId") Integer userId,
                   @Param("articleId") Integer articleId);

    //获取点赞数
    int selectLike(@Param("articleId") Integer articleId);

    //获取点赞排名
    ArrayList<Object> Top5CountLike();
}
