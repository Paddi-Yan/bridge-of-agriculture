package com.turing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.entity.User;
import com.turing.mapper.UserMapper;
import com.turing.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByOpenId(String openid) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("openid",openid));
    }
}
