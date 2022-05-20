package com.turing.service;

import com.turing.common.Result;
import com.turing.entity.User;

import java.util.Map;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月18日 21:18:08
 */
public interface AuthLoginService {

    String getSessionKey(String code);

    Map<String,Object> wechatLogin(String openid, String sessionKey, String nickname, String avatar) throws Exception;

    Map<String,Object> messageLogin(String phoneNumber, String code) throws Exception;
}
