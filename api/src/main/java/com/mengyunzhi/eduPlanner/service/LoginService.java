package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.model.CurrentUser;
import com.mengyunzhi.eduPlanner.model.Response;

public interface LoginService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Response<String> login(String username, String password);

    /**
     * 验证密码
     * @param user 用户
     * @param password 密码
     * @return 有效 true
     */
    boolean validatePassword(User user, String password);

    /**
     * 注销
     */
    Response<String> logout();

    /**
     * @return 当前登录用户，用户未登录则返回null
     */
    Response<CurrentUser> getCurrentLoginUser();
}
