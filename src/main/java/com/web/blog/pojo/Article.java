package com.web.blog.pojo;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Integer userID;
    private Integer topicID;
    private Date createTime;

    public Article(Integer id, String title, String content, Integer userID, Integer topicID, Date createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userID = userID;
        this.topicID = topicID;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
