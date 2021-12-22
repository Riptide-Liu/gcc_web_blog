package com.web.blog.service.impl;

import com.web.blog.mapper.CategoryMapper;
import com.web.blog.pojo.Category;
import com.web.blog.service.CategoryService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryMapper categoryMapper;
    @Autowired
    public void setDdDataMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    public int insertCategory(String topic, Integer parentId, Integer topicLevel) {
        int checkTopic = categoryMapper.selectByTopic(topic);
        if(checkTopic > 0) return 2;
        int result = categoryMapper.insertCategory(topic,parentId,topicLevel);
        return result;
    }

    @Override
    public int updateCategory(Integer id, String topic, Integer parentId, Integer topicLevel) {
        int result = categoryMapper.updateCategory(id,topic,parentId,topicLevel);
        return result;
    }

    @Override
    public int deleteCategory(Integer id) {
        int result = categoryMapper.deleteCategory(id);
        return result;
    }

    @Override
    public List<Category> selectCategory() {
        List<Category> items = categoryMapper.selectCategory();
        for(Category c : items){
            String topic = categoryMapper.selectOne(c.getParentId());
            c.setParentName(topic);
        }
        return items;
    }

    @Override
    public List<Category> selectLevel(Integer level) {
        List<Category> topics = categoryMapper.selectLevel(level-1);

        return topics;
    }
}
