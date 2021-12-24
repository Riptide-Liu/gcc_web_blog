package com.web.blog.mapper;

import com.web.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    // 发布
    int publish(@Param("title") String title,
                @Param("content") String content,
                @Param("userID") Integer userID,
                @Param("topicID") Integer topicID);

    // 修改
    int alterArticle(@Param("id") Integer id,
                     @Param("title") String title,
                     @Param("content") String content,
                     @Param("topicID") Integer topicID);

    // 删除
    int deleteArticle(@Param("id") Integer id);

    // 查看
    Article getArticle(@Param("id") Integer id);

    // 获取文章列表
    List<Article> getArticleList();

    // 根据用户获取文章数量
    int getBlogList(@Param("id") Integer id);

    // 根据用户获取文章
    List<Article> getArticleByUserId(@Param("id") Integer id);

    // 根据分类获取文章
    List<Article> getArticleByUserTopicId(@Param("topicId") Integer topicId);
}
