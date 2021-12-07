package com.web.blog.controller;

import com.web.blog.common.Result;
import com.web.blog.pojo.Article;
import com.web.blog.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin
public class ArticleController {

    private ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @PostMapping("/publish")
    public Result publish(@RequestParam(value = "title") String title,
                          @RequestParam(value = "content") String content,
                          @RequestParam(value = "userID") Integer userID,
                          @RequestParam(value = "topicID") Integer topicID
                          ){
        int result = this.articleService.publish(title, content, userID, topicID);
        return (result != 1 ? Result.fail("添加失败") : Result.succ("添加成功"));
    }

    @PostMapping("/alterArticle")
    public Result alterArticle(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "content") String content
                               ){
        int result = this.articleService.alterArticle(id, content);
        return (result != 1 ? Result.fail("修改失败") : Result.succ("修改成功"));
    }

    @PostMapping("/deleteArticle")
    public Result deleteArticle(@RequestParam(value = "id") Integer id){
        int result = this.articleService.deleteArticle(id);
        return (result != 1 ? Result.fail("删除失败") : Result.succ("删除成功"));
    }

    @PostMapping("/getArticle")
    public Result getArticle(@RequestParam(value = "id") Integer id){
        Article article = this.articleService.getArticle(id);
        return (article == null ?
                Result.fail("获取文章失败") : Result.succ(200, "获取文章成功", article));
    }
}
