package com.web.blog.service.impl;

import com.web.blog.mapper.ArticleMapper;
import com.web.blog.mapper.CommentMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.Article;
import com.web.blog.pojo.Comment;
import com.web.blog.pojo.User;
import com.web.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    private UserMapper userMapper;
    private ArticleMapper articleMapper;
    @Autowired
    public void setCommentMapper(CommentMapper commentMapper){
        this.commentMapper = commentMapper;
    }
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper){
        this.articleMapper = articleMapper;
    }

    @Override
    public int comment(Integer articleId, Integer userId, String content) {
        boolean articleExist = (articleMapper.getArticle(articleId) != null);
        boolean userExist = (userMapper.selectUsernameAndNickname(userId) != null);
        if (articleExist && userExist){
            return commentMapper.insertComment(articleId, userId, content);
        }else {
            return 0;
        }
    }

    @Override
    public int recallComment(Integer id) {
        return commentMapper.deleteComment(id);
    }

    @Override
    public ArrayList<Map<String, Object>> getCommentsByArticleId(Integer articleId) {
        ArrayList<Map<String, Object>> resultMapList = new ArrayList<>();
        ArrayList<Comment> commentList = commentMapper.selectCommentsByArticleId(articleId);
        User user;
        for (Comment comment : commentList){
            user = userMapper.selectUsernameAndNickname(comment.getUserId());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", comment.getId());
            resultMap.put("nickName", user.getNickname());
            resultMap.put("content", comment.getContent());
            resultMap.put("createTime", comment.getCreateTime());
            resultMapList.add(resultMap);
        }
        return resultMapList;
    }

    @Override
    public ArrayList<Map<String, Object>> getCommentsByUserId(Integer userId) {
        ArrayList<Map<String, Object>> resultMapList = new ArrayList<>();
        ArrayList<Comment> comments = commentMapper.selectCommentsByUserId(userId);
        Article article;
        for (Comment comment : comments){
            article = articleMapper.getArticle(comment.getArticleId());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", comment.getId());
            resultMap.put("articleTitle", article.getTitle());
            resultMap.put("content", comment.getContent());
            resultMap.put("createTime", comment.getCreateTime());
            resultMapList.add(resultMap);
        }
        return resultMapList;
    }
}
