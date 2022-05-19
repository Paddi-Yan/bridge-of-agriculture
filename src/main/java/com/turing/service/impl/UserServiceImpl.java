package com.turing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.RedisKey;
import com.turing.entity.User;
import com.turing.mapper.UserMapper;
import com.turing.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public User getUserByOpenId(String openid) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("openid",openid));
    }

    @Override
    public User bindEmail(String email, User user) {
        user.setEmail(email);
        userMapper.updateById(user);
        return user;
    }

    @Override
    public void bindMobile(Long userId, String phoneNumbers, String code) throws Exception {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id",userId));
        if(user == null) {
            throw new Exception("该用户不存在,绑定手机号失败!");
        }
        String validCode = (String) redisTemplate.opsForValue().get(RedisKey.MESSAGE_CODE_KEY + phoneNumbers);
        if(StringUtils.isEmpty(validCode) || !validCode.equals(code)) {
            throw new Exception("无效的验证码,请填写正确的验证码后重试!");
        }
        log.info("手机号[{}]验证码[{}]验证成功,开始绑定手机号",phoneNumbers,code);
        user.setMobile(phoneNumbers);
        userMapper.updateById(user);
    }
}
