package com.usher.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Usher
 * @Description:
 */
@Data
public class UserForm {

    @NotEmpty(message = "姓名")
    private String username;

    @NotEmpty(message = "密码")
    private String password;

    private String cid;
}
