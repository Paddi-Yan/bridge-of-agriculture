package com.turing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turing.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
public interface UserService extends IService<User> {

    User getUserByOpenId(String openid);

    User bindEmail(String email, User user);

    void bindMobile(Long userId, String phoneNumbers, String code) throws Exception;
}
