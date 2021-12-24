package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.ArticleDto;
import com.web.blog.dto.ArticlePreviewDto;
import com.web.blog.dto.UserDto;
import com.web.blog.pojo.Article;
import com.web.blog.pojo.Category;
import com.web.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ArticleController {

    private ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleDto articleDto){
        int result = this.articleService.publish(articleDto.getTitle(), articleDto.getContent(),
                articleDto.getUserID(), articleDto.getTopicID());
        return (result != 1 ? Result.fail("添加失败") : Result.succ("添加成功"));
    }

    @PostMapping("/alterArticle")
    public Result alterArticle(@RequestBody ArticleDto articleDto){
        int result = this.articleService.alterArticle(articleDto.getId(),articleDto.getTitle(),articleDto.getContent(),articleDto.getTopicID());
        return (result != 1 ? Result.fail("修改失败") : Result.succ("修改成功"));
    }

    @PostMapping("/deleteArticle")
    public Result deleteArticle(@RequestBody ArticleDto articleDto){
        int result = this.articleService.deleteArticle(articleDto.getId());
        return (result != 1 ? Result.fail("删除失败") : Result.succ("删除成功"));
    }

    @PostMapping("/getArticle")
    public Result getArticle(@RequestBody ArticleDto articleDto){
        Map<String, Object> article = this.articleService.getArticle(articleDto.getId());

        return (article == null ?
                Result.fail("获取文章失败") : Result.succ(200, "获取文章成功", article));
    }

    @PostMapping("/getArticleList")
    public Result getArticleList(@RequestBody UserDto userDto){
        ArrayList<Object> articleList = this.articleService.getArticleList(userDto.getId(),null);
        return (articleList == null ?
                Result.fail("获取文章列表失败") : Result.succ(200, "获取文章列表成功", articleList));
    }

    @PostMapping("/getUserArticle")
    public Result getUserArticle(@RequestBody UserDto userDto){
        ArrayList<Object> articles = articleService.getArticleByUserId(userDto.getId());
        return (articles == null ?
                Result.fail("获取用户文章列表失败") : Result.succ(200, "获取用户文章列表成功", articles));
    }
    @GetMapping("/getTop5Article")
    public Result getTop5Article(){
        ArrayList<Object> articles = articleService.getTop5Article();
        return (articles == null ?
                Result.fail("获取前五文章失败") : Result.succ(200, "获取前五文章成功", articles));
    }
    @PostMapping("/getArticleByCategory")
    public Result getArticleByCategory(@RequestBody Category category){
        ArrayList<Object> articles = articleService.getArticleByCategory(category.getUserId(),category.getId());
        return (articles == null ?
                Result.fail("获取用户文章列表失败") : Result.succ(200, "获取用户文章列表成功", articles));
    }
}
