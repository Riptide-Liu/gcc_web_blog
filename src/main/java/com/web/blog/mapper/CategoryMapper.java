package com.web.blog.mapper;

import com.web.blog.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Mapper
@Repository
public interface CategoryMapper {
    //创建分类
    int insertCategory(@Param("topic") String topic
            , @Param("parentId") Integer parentId
            , @Param("topicLevel") Integer topicLevel);
    //修改分类
    int updateCategory(@Param("id") Integer id
            , @Param("topic") String topic
            , @Param("parentId") Integer parentId
            , @Param("topicLevel") Integer topicLevel);
    //删除分类
    int deleteCategory(@Param("id") Integer id);

    //获取所有栏目
    List<Category> selectCategory();

    //获取栏目名
    String selectOne(@Param("id") Integer id);

    //获取层级
    List<Category> selectLevel(@Param("level") Integer level);

    //根据分类名查询
    Integer selectByTopic(@Param("topic") String topic);
}
