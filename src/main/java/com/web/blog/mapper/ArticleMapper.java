package com.web.blog.mapper;

import com.web.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
                     @Param("content") String content);

    // 删除
    int deleteArticle(@Param("id") Integer id);

    // 查看
    Article getArticle(@Param("id") Integer id);
}
