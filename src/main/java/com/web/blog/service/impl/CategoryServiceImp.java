package com.web.blog.service.impl;

import com.web.blog.mapper.CategoryMapper;
import com.web.blog.pojo.Category;
import com.web.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryMapper categoryMapper;
    @Autowired
    public void setDdDataMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    public int insertCategory(String topic, String desc) {

        int result = categoryMapper.insertCategory(topic,desc);
        return result;
    }

    @Override
    public int updateCategory(Integer id, String topic, String desc) {
        int result = categoryMapper.updateCategory(id,topic,desc);
        return result;
    }

    @Override
    public int deleteCategory(Integer id) {
        int result = categoryMapper.deleteCategory(id);
        return result;
    }

    @Override
    public List<Category> selectCategory() {
        List<Category> list = categoryMapper.selectCategory();
        return list;
    }
}
