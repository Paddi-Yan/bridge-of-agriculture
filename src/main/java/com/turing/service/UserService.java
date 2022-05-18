package com.turing.service;

import com.turing.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
public interface UserService extends IService<User> {

    User getUserByOpenId(String openid);
}
