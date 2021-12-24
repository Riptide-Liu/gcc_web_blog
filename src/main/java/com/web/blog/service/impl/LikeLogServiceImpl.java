package com.web.blog.service.impl;

import com.web.blog.mapper.LikeLogMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.service.LikeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LikeLogServiceImpl implements LikeLogService {
    private LikeLogMapper likeLogMapper;
    @Autowired
    public void setLikeLogMapper(LikeLogMapper likeLogMapper) {
        this.likeLogMapper = likeLogMapper;
    }
    @Override
    public int insertLike(Integer userId, Integer articleId) {

        return likeLogMapper.insertLike(userId,articleId);
    }

    @Override
    public int deleteLike(Integer userId, Integer articleId) {
        return likeLogMapper.deleteLike(userId, articleId);
    }

    @Override
    public int selectLike(Integer articleId) {
        return likeLogMapper.selectLike(articleId);
    }

    @Override
    public ArrayList<Object> Top5CountLike() {
        List<Map<String, Object>> list = likeLogMapper.selectCountLike();
        ArrayList<Object> result = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            if(i == list.size())
                break;

            for(String key : list.get(i).keySet()){
                if(key.equals("articleId"))
                    System.out.println(key+" "+list.get(i).get(key));


            }
             result.add(list.get(i));
        }
        return result;
    }
}
