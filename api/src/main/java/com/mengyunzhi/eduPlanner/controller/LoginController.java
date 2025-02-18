package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("Login")
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    LoginService loginService;

    @Autowired
    StudentRepository studentRepository;

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
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        logger.info("currentUser:" + currentUser);
        return this.loginService.getCurrentLoginUser();
    }

    @GetMapping("currentStudent")
    public Response<Student> getCurrentStudent() {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long role = currentUser.getData().getRole();
        if (role == 1) {
            Long userId = currentUser.getData().getId();
            Student currentStudent = this.studentRepository.findByUserId(userId);
            return Response.success(currentStudent);
        } else {
            return Response.fail("获取当前登录学生失败");
        }
    }
}
