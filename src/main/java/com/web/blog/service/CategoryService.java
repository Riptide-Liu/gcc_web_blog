package com.web.blog.service;

import com.web.blog.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
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

    //获取层级
    List<Category> selectLevel(@Param("level") Integer level);
}
