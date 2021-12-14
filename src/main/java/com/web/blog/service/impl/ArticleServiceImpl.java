package com.web.blog.service.impl;

import com.web.blog.dto.ArticlePreviewDto;
import com.web.blog.mapper.ArticleMapper;
import com.web.blog.mapper.CategoryMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.Article;
import com.web.blog.pojo.Category;
import com.web.blog.pojo.User;
import com.web.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
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
    public Map<String, Object> getArticle(Integer id) {
        Article article = articleMapper.getArticle(id);
        if(article == null)
            return null;
        User user = userMapper.selectUsernameAndNickname(article.getUserID());
        Category category = categoryMapper.selectOne(article.getTopicID());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("article",article);
        resultMap.put("nickname",user.getNickname());
        resultMap.put("username",user.getUsername());
        resultMap.put("topic",category.getTopic());
        return resultMap;
    }

    @Override
    public ArrayList<Object> getArticleList() {
        ArrayList<Object> resultList = new ArrayList<>();
        String content = "";
        for (Article article : articleMapper.getArticleList()){
            String _content = article.getContent();
            if (_content.length() < 50){
                content = _content;
            }
            else {
                content = _content.substring(0, (int) Math.round(_content.length() * 0.2)) + "...";
            }
            User user = userMapper.selectUsernameAndNickname(article.getUserID());
            Category category = categoryMapper.selectOne(article.getTopicID());
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("articlePreview",new ArticlePreviewDto(article.getId(), article.getTitle(), content, article.getUserID(),
                    article.getTopicID(), article.getUpdateTime()));
            resultMap.put("nickname",user.getNickname());
            resultMap.put("username",user.getUsername());
            resultMap.put("topic",category.getTopic());
            resultList.add(resultMap);
        }
        return resultList;
    }
}
