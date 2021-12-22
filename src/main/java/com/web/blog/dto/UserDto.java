package com.web.blog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto  implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private Integer id;
    private String email;
    private String old_password;

}
