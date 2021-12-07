package com.web.blog.service.impl;

import com.web.blog.mapper.ArticleMapper;
import com.web.blog.pojo.Article;
import com.web.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public int publish(String title, String content, Integer userID, Integer topicID) {
        return articleMapper.publish(title, content, userID, topicID);
    }

    @Override
    public int alterArticle(Integer id, String content) {
        return articleMapper.alterArticle(id, content);
    }

    @Override
    public int deleteArticle(Integer id) {
        return articleMapper.deleteArticle(id);
    }

    @Override
    public Article getArticle(Integer id) {
        return articleMapper.getArticle(id);
    }
}
