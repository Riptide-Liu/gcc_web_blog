package com.web.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LikeLogMapper {
    //点赞
    int insertLike(@Param("userId") Integer userId,
                   @Param("articleId") Integer articleId);

    //取消点赞
    int deleteLike(@Param("userId") Integer userId,
                   @Param("articleId") Integer articleId);

    //获取点赞数
    int selectLike(@Param("articleId") Integer articleId);
}
