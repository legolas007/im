package com.usher.service;


import com.usher.pojo.Users;

/**
 * @Author: Usher
 * @Description:
 */

public interface UserService {
    /**
     * 用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 用户是否存在
     * @param username
     * @param pwd
     * @return
     */
    Users queryUserForLogin(String username, String pwd);

    /**
     * 注册
     * @param user
     * @return
     */
    Users saveUser(Users user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    Users updateUserInfo(Users user);


}
