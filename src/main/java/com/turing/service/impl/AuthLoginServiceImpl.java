package com.turing.service.impl;

import cn.hutool.http.HttpUtil;
import com.turing.common.RedisKey;
import com.turing.common.Result;
import com.turing.entity.User;
import com.turing.mapper.UserMapper;
import com.turing.service.AuthLoginService;
import com.turing.service.UserService;
import com.turing.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月18日 21:20:13
 */
@Service
@Slf4j
public class AuthLoginServiceImpl implements AuthLoginService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Value("${wechat-mini.appid}")
    private String appid;

    @Value("${wechat-mini.secret}")
    private String secret;

    @Override
    public String getSessionKey(String code) {
        log.info("appid:{}", appid);
        log.info("secret:{}", secret);
        log.info("code:{}", code);
        //调用微信登录凭证校验接口
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String replaceUrl = url.replace("APPID", appid)
                               .replace("SECRET", secret)
                               .replace("JSCODE", code)
                               .replace("'", "");
        log.info("URL:{}", replaceUrl);
        return HttpUtil.get(replaceUrl);
    }

    @Override
    public User wechatLogin(String openid, String sessionKey, String nickname, String avatar) throws Exception {
        //通过openid唯一标识取查询数据库是否有用户信息
        User user = userService.getUserByOpenId(openid);
        if(user == null) {
            //注册
            user = new User();
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setOpenid(openid);
            user.setRegisterTime(LocalDateTime.now());
            return this.registry(user);
        }else {
            //登录
            user.setNickname(nickname);
            user.setAvatar(avatar);
            userMapper.updateById(user);
            return this.login(user);
        }
    }

    private User registry(User user) throws Exception {
        int count = userMapper.insert(user);
        if(count != 1) {
            throw new Exception("注册失败!");
        }
        return this.login(user);
    }

    private User login(User user) {
        String token = JWTUtils.sign(user.getId());
        log.info("用户[{}]登录有效凭证[{}]",user, RedisKey.TOKEN+token);
        redisTemplate.opsForValue().set(RedisKey.TOKEN+token,user,7, TimeUnit.DAYS);
        return user;
    }
}
