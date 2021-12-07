package com.web.blog.dto;

import java.io.Serializable;

public class UserDto  implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private Integer id;


    public UserDto() {
    }

    public UserDto(String username, String password, String nickname, Integer id) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
