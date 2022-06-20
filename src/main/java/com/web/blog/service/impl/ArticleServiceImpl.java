package com.web.blog.service.impl;

import com.web.blog.dto.ArticlePreviewDto;
import com.web.blog.mapper.*;
import com.web.blog.pojo.Article;
import com.web.blog.pojo.Category;
import com.web.blog.pojo.LikeLog;
import com.web.blog.pojo.User;
import com.web.blog.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private LikeLogMapper likeLogMapper;
    @Autowired
    public void setLikeLogMapper(LikeLogMapper likeLogMapper) {
        this.likeLogMapper = likeLogMapper;
    }
    private CommentMapper commentMapper;
    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public int publish(String title, String content, Integer userID, Integer topicID) {
        return articleMapper.publish(title, content, userID, topicID);
    }

    @Override
    public int alterArticle(Integer id,String title, String content,Integer topicId) {
        return articleMapper.alterArticle(id, title,content,topicId);
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
        String category = categoryMapper.selectOne(article.getTopicID());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("article",article);
        resultMap.put("nickname", user == null ? null : user.getNickname());
        resultMap.put("username", user == null ? null : user.getUsername());
        resultMap.put("topic", category == null ? null : category);
        return resultMap;
    }

    @Override
    public ArrayList<Object> getArticleList(Integer id, Integer parentId) {
        List<LikeLog> likes = likeLogMapper.selectLikeByUserId(id);
        ArrayList<Object> resultList = new ArrayList<>();
        String content = "";
        for (Article article : parentId == null ? articleMapper.getArticleList():articleMapper.getArticleByUserTopicId(parentId)){

            String _content = article.getContent();
            System.out.println(_content.length());
            if (_content.length() < 50){
                content = _content;
            }
            else {
                content = _content.substring(0, (int) Math.round(_content.length() * 0.2)) + "...";
            }
            User user = userMapper.selectUsernameAndNickname(article.getUserID());
            String category = categoryMapper.selectOne(article.getTopicID());
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("articlePreview",new ArticlePreviewDto(article.getId(), article.getTitle(), content, article.getUserID(),
                    article.getTopicID(), article.getUpdateTime()));
            resultMap.put("nickname", user == null ? null : user.getNickname());
            resultMap.put("username", user == null ? null : user.getUsername());
            resultMap.put("topic", category == null ? null : category);
            resultMap.put("isLike",false);
            //判断是否已经点赞
            if(id != null){
                for(LikeLog ll : likes){
                    if(ll.getArticleId().equals(article.getId())){
                        resultMap.put("isLike",true);
                        likes.remove(ll);
                        break;
                    }
                }
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public ArrayList<Object> getArticleByUserId(Integer id) {
        ArrayList<Object> resultList = new ArrayList<>();
        List<Article> list = articleMapper.getArticleByUserId(id);
        for(Article article : list){
            String category = categoryMapper.selectOne(article.getTopicID());
            User user = userMapper.selectUsernameAndNickname(article.getUserID());
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id", article.getId());
            resultMap.put("title",article.getTitle());
            resultMap.put("topicId",article.getTopicID());
            resultMap.put("topic", category);
            resultMap.put("content", article.getContent());
            resultMap.put("userId", article.getUserID());
            resultMap.put("nickname", user == null ? null : user.getNickname());
            resultMap.put("username", user == null ? null : user.getUsername());
            resultMap.put("likesNum",likeLogMapper.selectLike(article.getId()));
            resultMap.put("commentNum",commentMapper.selectCommentNumber(article.getId()));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public ArrayList<Object> getTop5Article() {
        List<Map<String, Object>> list = likeLogMapper.selectCountLike();
        ArrayList<Object> result = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            if(i == list.size())
                break;
            Map<String, Object> item = new HashMap<>();
            for(String key : list.get(i).keySet()){
                if(key.equals("articleId")){
                    Article article = articleMapper.getArticle((int)list.get(i).get(key));
                    if(article == null) continue;
                    item.put("title",article.getTitle());
                    item.put("articleId",article.getId());
                }
                else
                    item.put("like",list.get(i).get(key));
            }
            result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Object> getArticleByCategory(Integer userId, Integer parentId) {
        ArrayList<Category> categoryList = new ArrayList<>();
        List<Category> list = categoryMapper.SelectByParentId(parentId);
        Category self_category = categoryMapper.selectById(parentId);
        categoryList.add(self_category);
        //拿到分类
        for(Category category : list){
            categoryList.add(category);
            if(category.getParentId() !=null && category.getTopicLevel() < 3){
                List<Category> b_list = categoryMapper.SelectByParentId(category.getId());
                for(Category b_category : b_list)
                    categoryList.add(b_category);
            }
        }
        //根据分类获取文章
        ArrayList<Object> articleList = new ArrayList<>();
        for (Category AL : categoryList){
            ArrayList<Object> temp = getArticleList(userId,AL.getId());
            for(Object item : temp)
                articleList.add(item);
        }
        return articleList;
    }
}
