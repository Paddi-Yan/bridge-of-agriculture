package com.turing.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.turing.common.*;
import com.turing.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年04月14日 19:52:44
 */
@Component
@Slf4j
public class AuthenticateInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.hasMethodAnnotation(PassToken.class)) {
            return true;
        }
        String token = request.getHeader(JWTUtils.AUTH_HEADER_KEY);
        if(StringUtils.isBlank(token)) {
            return errorResponse(response);
        }
        token = token.replace(JWTUtils.TOKEN_PREFIX, "");
        boolean verify = JWTUtils.verify(token);
        if(!verify) {
            return errorResponse(response);
        }
        //是否存在改token
        Boolean exited = redisTemplate.hasKey(RedisKey.TOKEN+token);
        if(!exited) {
            return errorResponse(response);
        }
        if(JWTUtils.isExpired(token)) {
            return errorResponse(response);
        }
        TokenInfo tokenInfo = (TokenInfo) redisTemplate.opsForValue().get(token);
        if(tokenInfo == null || !tokenInfo.getId().equals(JWTUtils.getUserId(token))) {
            return errorResponse(response);
        }
        return true;
    }

    private boolean errorResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(HttpStatusCode.UNAUTHORIZED)));
        return false;
    }
}
