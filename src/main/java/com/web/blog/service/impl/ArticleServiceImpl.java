package com.web.blog.service.impl;

import com.web.blog.dto.ArticlePreviewDto;
import com.web.blog.mapper.ArticleMapper;
import com.web.blog.pojo.Article;
import com.web.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ArrayList<ArticlePreviewDto> getArticleList() {
        ArrayList<ArticlePreviewDto> resultList = new ArrayList<>();
        String content = "";
        for (Article article : articleMapper.getArticleList()){
            String _content = article.getContent();
            int chopLength = (int) Math.round(_content.length() * 0.2);
            if (_content.length() < 50){
                content = _content;
            }
            else {
                content = _content.substring(0, chopLength) + "...";
            }
            resultList.add(new ArticlePreviewDto(article.getId(), article.getTitle(), content, article.getUserID(),
                    article.getTopicID(), article.getUpdateTime()));
        }
        return resultList;
    }
}
