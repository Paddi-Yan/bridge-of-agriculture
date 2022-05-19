package com.turing.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月19日 19:20:58
 */
public interface SendSmsService {

    void sendMessage(String phoneNumbers) throws Exception;
}
