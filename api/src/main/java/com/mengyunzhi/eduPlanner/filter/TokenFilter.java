package com.mengyunzhi.eduPlanner.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

/**
 * 令牌过滤器
 * 继承HttpFilter以过滤http请求与响应
 */
@WebFilter
public class TokenFilter extends HttpFilter {
    private final static Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    public static String TOKEN_KEY = "auth-token";
    // 存储已经发放过的令牌
    private HashSet<String> tokens = new HashSet<>();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取header中的token
        String token = request.getHeader(TOKEN_KEY);
        logger.info("获取到的token为" + token);

        // 有效性验证
        if (!this.validateToken(token)) {
            // 如果无效则分发送的token
            token = this.makeToken();
            logger.info("原token无效，发布新的token为" + token);

            // 设置header中的auth-token 此时的request已经是那个被重写过getHeader方法的HttpServletRequestTokenWrapper了
            request = new HttpServletRequestTokenWrapper(request, token);
        }

        // 在确立响应信息前，设置响应的header值
        response.setHeader(TOKEN_KEY, token);

        // 转发数据，spring开始调用控制器中的特定方法
        chain.doFilter(request, response);
    }

    /**
     * 验证token的有效性，传入的令牌如果是系统分发，则有效，如果系统未分发过该令牌，视为无效
     * @param token token
     * @return boolean
     */
    private boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        return this.tokens.contains(token);
    }

    /**
     * 生成token
     * 将生成的token存入已分发的tokens中
     * @return token
     */
    private String makeToken() {
        // 获取非重复的随机字符串
        String token = UUID.randomUUID().toString();
        this.tokens.add(token);
        return token;
    }

    /**
     * 带有请求token的Http请求
     */
    class HttpServletRequestTokenWrapper extends HttpServletRequestWrapper {
        HttpServletRequestWrapper httpServletRequestWrapper;
        String token;

        /**
         * 实现父类构造函数，为了防止其被外部使用从而声明为私有
         */
        private HttpServletRequestTokenWrapper(HttpServletRequest request) {
            super(request);
        }

        public HttpServletRequestTokenWrapper(HttpServletRequest request, String token) {
            this(request);
            this.token = token;
        }

        @Override
        public String getHeader(String name) {
            if (TOKEN_KEY.equals(name)) {
                return this.token;
            }
            return super.getHeader(name);
        }
    }
}
