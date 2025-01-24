package com.mengyunzhi.eduPlanner.interceptor;

import com.mengyunzhi.eduPlanner.filter.TokenFilter;
import com.mengyunzhi.eduPlanner.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private final LoginService loginService;

    @Autowired
    public AuthInterceptor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求地址及请求方法
        String url = request.getRequestURI();
        String method = request.getMethod();

        if ("OPTIONS".equals(method)) {
            // 请求方法为OPTIONS，不拦截
            return true;
        }

        // 判断请求地址、方法是否与用户登录相同
        if ("/Login".equals(url) && "POST".equals(method)) {
            return true;
        }

        // auth-token是否绑定了用户
        String authToken = request.getHeader(TokenFilter.TOKEN_KEY);
        if (this.loginService.isLogin(authToken)) {
            return true;
        }

        // 为响应加入提示：用户未登录
        response.setStatus(401);
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,PATCH");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("执行拦截器postHandle");
    }
}
