package com.web.blog.dto;

import java.io.Serializable;

public class CategoryDto implements Serializable {
    private Integer id;
    private String topic;
    private String desc;

    public CategoryDto() {
    }

    public CategoryDto(Integer id, String topic, String desc) {
        this.id = id;
        this.topic = topic;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
