package com.mengyunzhi.eduPlanner.service;


import com.mengyunzhi.eduPlanner.entity.*;
import com.mengyunzhi.eduPlanner.filter.TokenFilter;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.repository.AdminRepository;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final HashMap<String, Long> authTokenUserIdHashMap = new HashMap<>();

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    // 使用@Autowired对构造函数进行注解
    @Autowired
    public LoginServiceImpl(UserRepository userRepository,
                            StudentRepository studentRepository,
                            AdminRepository adminRepository,
                            HttpServletRequest request) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.request = request;
    }

    @Override
    public Response<String> login(String username, String password) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            return new Response<>(false, "用户名不存在", "null");
        }
        if (!this.validatePassword(user, password)) {
            // 认证不成功直接返回
            return new Response<>(false, "密码错误", "null");
        }

        // 认证成功，进行auth-token与userId的绑定 向hashMap中存数据使用put方法 键值对
        logger.info("获取到的auth-token为" + this.request.getHeader(TokenFilter.TOKEN_KEY));
        this.authTokenUserIdHashMap.put(this.request.getHeader(TokenFilter.TOKEN_KEY), user.getId());
        return new Response<>(true, "登录成功", "null");
    }

    @Override
    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    @Override
    public Response<String> logout() {
        // 获取auth-token
        String authToken = this.request.getHeader("auth-token");
        logger.info("获取到的auth-token为" + this.request.getHeader("auth-token"));

        // 删除hashMap中对应auth-token的映射
        this.authTokenUserIdHashMap.remove(authToken);
        return new Response<>(true, "注销成功", "null");
    }

    @Override
    public Response<CurrentUser> getCurrentLoginUser() {
        String authToken = this.request.getHeader("auth-token");
        // 获取authToken映射的userId
        Long userId = this.authTokenUserIdHashMap.get(authToken);
        if (userId == null) {
            // 未获取到userId,说明该authToken未与用户进行绑定，返回null
            return null;
        }
        // 如果获取到userId，则由数据库中获取user并返回
        Optional<User> user = this.userRepository.findById(userId);
        Long role = user.get().getRole();
        // 如果当前登录用户为学生，查找出该学生对应的学校id
        if (role == 1) {
            Student student = this.studentRepository.findByUserId(userId);
            Clazz clazz = student.getClazz();
            String name = student.getName();
            String no = student.getSno();
            School school = clazz.getSchool();
            Long schoolId = school.getId();
            CurrentUser userDetails = new CurrentUser(
                    user.get().getId(),
                    name,
                    user.get().getUsername(),
                    user.get().getPassword(),
                    user.get().getRole(),
                    schoolId,
                    no);
            return new Response<>(true, "成功获取用户", userDetails);
        }

        Admin admin = this.adminRepository.findByUserId(userId);
        String name = admin.getName();
        String no = admin.getAno();
        CurrentUser userIsAdmin = new CurrentUser(
                user.get().getId(),
                name,
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().getRole(),
                null,
                no
        );
        return new Response<>(true, "成功获取用户admin", userIsAdmin);
    }

    @Override
    public boolean isLogin(String authToken) {
        Long userId = this.authTokenUserIdHashMap.get(authToken);
        return userId != null;
    }
}
