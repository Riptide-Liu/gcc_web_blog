package com.web.blog.service;

import com.web.blog.pojo.Article;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    // 发布
    int publish(@Param("title") String title,
                @Param("content") String content,
                @Param("userID") Integer userID,
                @Param("topicID") Integer topicID
                );

    // 修改
    int alterArticle(@Param("id") Integer id,
                     @Param("title") String title,
                     @Param("content") String content,
                     @Param("topicID") Integer topicID);

    // 删除
    int deleteArticle(@Param("id") Integer id);

    // 查看
    Map<String, Object> getArticle(@Param("id") Integer id);

    // 获取文章列表
    ArrayList<Object> getArticleList(@Param("id") Integer id, @Param("parentId") Integer parentId);

    // 根据用户获取文章
    ArrayList<Object> getArticleByUserId(@Param("id") Integer id);

    //获取点赞前五文章
    ArrayList<Object> getTop5Article();

    //根据分类名获取文章
    ArrayList<Object> getArticleByCategory(@Param("userId") Integer UserId,@Param("parentId") Integer parentId);
}
