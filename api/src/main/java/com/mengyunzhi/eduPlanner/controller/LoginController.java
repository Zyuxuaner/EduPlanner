package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping
    public Response<String> login(@RequestBody User user) {
        return this.loginService.login(user.getUsername(), user.getPassword());
    }

    @GetMapping
    public Response<String> logout() {
        return this.loginService.logout();
    }

    @GetMapping("currentLoginUser")
    public Response<CurrentUser> getCurrentLoginUser() {
        return this.loginService.getCurrentLoginUser();
    }
}
