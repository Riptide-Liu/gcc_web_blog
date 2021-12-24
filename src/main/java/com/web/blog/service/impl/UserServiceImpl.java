package com.web.blog.service.impl;

import com.web.blog.mapper.ArticleMapper;
import com.web.blog.mapper.UserMapper;
import com.web.blog.pojo.User;
import com.web.blog.pojo.Visitor;
import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    @Autowired
    public void setDdDataMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    private ArticleMapper articleMapper;
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public User selectOne(String username, String password) {
        User user = userMapper.selectOne(username,password);
        if(user != null)
            return user.getEnable() == 0 ? null : user;
        else
            return null;
    }

    @Override
    public int insertUser(String username, String password, String nickname, String email) {
        List<User> checkUsername = userMapper.selectUsername(username);
        if(checkUsername.size()>1)
            return 2;

        return userMapper.insertUser(username,password,nickname,email);
    }

    @Override
    public int updatePassword(Integer id,String old_password,String password) {
        int result = 0;
        User check_pwd = userMapper.checkPassword(id,old_password);
        if(check_pwd!=null)
            result = userMapper.updatePassword(id,password);
        return result;
    }

    @Override
    public int updateUserInfo(Integer id, String nickname, String email) {
        int result = userMapper.updateUserInfo(id,nickname,email);
        return result;
    }

    @Override
    public int disableUser(Integer id) {
        int result = userMapper.disableUser(id);
        return result;
    }

    @Override
    public int enableUser(Integer id) {
        int result = userMapper.enableUser(id);
        return result;
    }

    @Override
    public ArrayList<Object> selectAllUser() {
        ArrayList<Object> resultList = new ArrayList<>();
        for (User item:userMapper.selectAllUser()){
            Map<String,Object> result = new HashMap<>();
            result.put("id",item.getId());
            result.put("username",item.getUsername());
            result.put("nickname",item.getNickname());
            result.put("level",item.getLevel());
            result.put("enable",item.getEnable());
            result.put("email",item.getEmail());
            result.put("blogNumber",articleMapper.getBlogList(item.getId()));
            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public User selectUser(Integer id) {
        return userMapper.selectUser(id);
    }

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String ymd = simpleDateFormat.format(date);
    @Override
    public int updateVisitor(Integer userID) {
        if(userMapper.selectVisitor(userID,ymd) == null)
            userMapper.insertVisitor(userID,ymd);
        return userMapper.updateVisitor(userID,ymd);
    }

    @Override
    public Visitor selectVisitor(Integer userID, String ymd) {
        return null;
    }

    @Override
    public int insertVisitor(Integer userID, String ymd) {
        return 0;
    }

    @Override
    public int selectVisitorNum(Integer userID) {
        if(userMapper.selectVisitor(userID,ymd) == null)
            userMapper.insertVisitor(userID,ymd);
        return userMapper.selectUserVisitor(userID,ymd);
    }

    @Override
    public Object selectVisitorNum() {
        if(userMapper.selectVisitor(1,ymd) == null)
            userMapper.insertVisitor(1,ymd);
        Object num = userMapper.selectAllVisitor(ymd);
        return num;
    }

    @Override
    public ArrayList<Object> selectALLDayUserVisitor(Integer userID) {
        if(userMapper.selectVisitor(userID,ymd) == null)
            userMapper.insertVisitor(userID,ymd);
        int oneWeek = 7;
        List<Object> list = userMapper.selectALLDayUserVisitor(userID);
        ArrayList<Object> resultList = new ArrayList<>();
        for (Object visitor : list){
            if(oneWeek == 0) break;
            resultList.add(visitor);
            oneWeek --;
        }
        Collections.reverse(resultList);
        return resultList;
    }

    @Override
    public ArrayList<Object> selectALLDayUserVisitor() {
        if(userMapper.selectVisitor(1,ymd) == null)
            userMapper.insertVisitor(1,ymd);
        int oneWeek = 7;
        ArrayList<Object> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year =  calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        for (int i=0;i<oneWeek;i++){
            Map<String, Object> map = new HashMap<>();
            String n_ymd = year + "-" + month + "-" + day + "";
            if(userMapper.selectAllVisitor(n_ymd) == null)
                continue;
            Object visitor = userMapper.selectAllVisitor(n_ymd);
            map.put("ymd",n_ymd);
            map.put("visitor",visitor);
            result.add(map);
            day --;
        }
        Collections.reverse(result);
        return result;
    }
}
