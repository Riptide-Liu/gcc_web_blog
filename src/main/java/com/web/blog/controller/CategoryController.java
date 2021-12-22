package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.CategoryDto;
import com.web.blog.pojo.Category;
import com.web.blog.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin //所有域名均可访问该类下所有接口
@Api(tags = "栏目管理模块")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //创建栏目
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody CategoryDto categoryDto){
        int result = categoryService.insertCategory(categoryDto.getTopic(),categoryDto.getParentId(),categoryDto.getTopicLevel());
        if(result == 2)
            return Result.fail("创建失败！已存在分类。");
        return result == 1 ? Result.succ(200,"创建成功！",result) : Result.fail("创建失败！",result);
    }

    //修改栏目
    @PostMapping("/editCategory")
    public Result editCategory(@RequestBody CategoryDto categoryDto){
        int result = categoryService.updateCategory(categoryDto.getId(),categoryDto.getTopic(),categoryDto.getParentId(),categoryDto.getTopicLevel());
        return result == 1 ? Result.succ(200,"修改成功！",result) : Result.fail("修改失败！",result);
    }

    //删除栏目
    @PostMapping("/deleteCategory")
    public Result deleteCategory(@RequestBody CategoryDto categoryDto){
        int result = categoryService.deleteCategory(categoryDto.getId());
        return result == 1 ? Result.succ(200,"删除成功！",result) : Result.fail("删除失败！",result);
    }

    //获取所有栏目
    @GetMapping("/categoryList")
    public Result getCategoryList(){
        List<Category> list = categoryService.selectCategory();
        return list != null ? Result.succ(list) : Result.fail("获取所有栏目失败！");
    }

    //获取父层级
    @PostMapping("/topicParentLevel")
    public Result topicParentLevel(@RequestBody CategoryDto categoryDto){
        if(categoryDto.getTopicLevel() == null)
            return Result.fail("请检查参数是否正确");
        List<Category> list = categoryService.selectLevel(categoryDto.getTopicLevel());
        return list != null ? Result.succ(list) : Result.fail("获取层级失败！");
    }
}
