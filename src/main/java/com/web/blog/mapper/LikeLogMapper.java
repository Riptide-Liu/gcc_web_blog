package com.web.blog.mapper;

import com.web.blog.pojo.LikeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    //获取点赞排名
    List<Map<String,Object>> selectCountLike();

    //根据用户查询点赞
    List<LikeLog> selectLikeByUserId(@Param("userId") Integer userId);
}
