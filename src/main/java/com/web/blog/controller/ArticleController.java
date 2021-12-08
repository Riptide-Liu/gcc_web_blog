package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.dto.ArticleDto;
import com.web.blog.dto.ArticlePreviewDto;
import com.web.blog.pojo.Article;
import com.web.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        int result = this.articleService.alterArticle(articleDto.getId(), articleDto.getContent());
        return (result != 1 ? Result.fail("修改失败") : Result.succ("修改成功"));
    }

    @PostMapping("/deleteArticle")
    public Result deleteArticle(@RequestBody ArticleDto articleDto){
        int result = this.articleService.deleteArticle(articleDto.getId());
        return (result != 1 ? Result.fail("删除失败") : Result.succ("删除成功"));
    }

    @PostMapping("/getArticle")
    public Result getArticle(@RequestBody ArticleDto articleDto){
        Article article = this.articleService.getArticle(articleDto.getId());
        return (article == null ?
                Result.fail("获取文章失败") : Result.succ(200, "获取文章成功", article));
    }

    @PostMapping("/getArticleList")
    public Result getArticleList(){
        ArrayList<ArticlePreviewDto> articleList = this.articleService.getArticleList();
        return (articleList == null ?
                Result.fail("获取文章列表失败") : Result.succ(200, "获取文章列表成功", articleList));
    }
}
