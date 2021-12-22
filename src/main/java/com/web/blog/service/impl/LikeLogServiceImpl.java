package com.web.blog.service.impl;

import com.web.blog.mapper.LikeLogMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.service.LikeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeLogServiceImpl implements LikeLogService {
    private LikeLogMapper likeLogMapper;
    @Autowired
    public void setLikeLogMapper(LikeLogMapper likeLogMapper) {
        this.likeLogMapper = likeLogMapper;
    }
    @Override
    public int insertLike(Integer userId, Integer articleId) {
        return 0;
    }

    @Override
    public int deleteLike(Integer userId, Integer articleId) {
        return 0;
    }

    @Override
    public int selectLike(Integer articleId) {
        return 0;
    }
}
