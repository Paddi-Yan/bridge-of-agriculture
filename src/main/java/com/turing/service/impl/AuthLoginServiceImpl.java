package com.turing.service.impl;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.RedisKey;
import com.turing.common.TokenInfo;
import com.turing.entity.User;
import com.turing.entity.vo.UserVo;
import com.turing.mapper.UserMapper;
import com.turing.service.AuthLoginService;
import com.turing.service.UserService;
import com.turing.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    public Map<String, Object> wechatLogin(String openid,
                                           String sessionKey,
                                           String nickname,
                                           String avatar) throws Exception {
        //通过openid唯一标识取查询数据库是否有用户信息
        User user = userService.getUserByOpenId(openid);
        return commonLogin(openid, nickname, avatar, user);
    }

    private Map<String, Object> commonLogin(String openid,
                                            String nickname,
                                            String avatar,
                                            User user) throws Exception {
        if(user == null) {
            //注册
            user = new User();
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setOpenid(openid);
            user.setRegisterTime(LocalDateTime.now());
            return this.registry(user);
        } else {
            //登录
            user.setNickname(nickname);
            user.setAvatar(avatar);
            userMapper.updateById(user);
            return this.login(user);
        }
    }

    @Override
    public Map<String, Object> messageLogin(String phoneNumber, String code) throws Exception {
        String validCode = (String) redisTemplate.opsForValue().get(RedisKey.MESSAGE_CODE_KEY + phoneNumber);
        if(StringUtils.isEmpty(validCode) || !validCode.equals(code)) {
            throw new Exception("短信验证码不合法,请确认后重试!");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("mobile", phoneNumber));
        if(user == null) {
            //注册
            return commonLogin(null, null, null, null);
        } else {
            //登录
            return commonLogin(user.getOpenid(), user.getNickname(), user.getAvatar(), user);
        }
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisKey.TOKEN + token);
    }

    private Map<String, Object> registry(User user) throws Exception {
        int count = userMapper.insert(user);
        if(count != 1) {
            throw new Exception("注册失败!");
        }
        log.info("新用户注册成功:{}", user);
        return this.login(user);
    }

    private Map<String, Object> login(User user) {
        String token = JWTUtils.sign(user.getId());
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(user.getId());
        tokenInfo.setName(user.getNickname());
        tokenInfo.setType(TokenInfo.USER_TYPE);
        log.info("用户登录成功:{}", user);
        log.info("用户[{}]登录有效凭证[{}]", user, RedisKey.TOKEN + token);
        redisTemplate.opsForValue().set(RedisKey.TOKEN + token, tokenInfo, 7, TimeUnit.DAYS);
        HashMap<String, Object> result = new HashMap<>();
        result.put("token", token);
        UserVo userVo = new UserVo();
        userVo.transform(user);
        result.put("userInfo", userVo);
        return result;
    }
}
