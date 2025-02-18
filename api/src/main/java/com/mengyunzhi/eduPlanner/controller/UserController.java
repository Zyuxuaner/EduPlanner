package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("User")
public class UserController {
    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public UserController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     * 修改用户密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 响应结果
     */
    @GetMapping("/changePassword")
    public Response<String> changePassword(@RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword) {
        // 获取当前登录用户
        Response<CurrentUser> currentUserResponse = loginService.getCurrentLoginUser();
        if (currentUserResponse == null) {
            return new Response<>(false, "用户未登录", null);
        }
        Long userId = currentUserResponse.getData().getId();

        // 调用服务层方法修改密码
        return userService.changePassword(userId, oldPassword, newPassword);
    }
}
