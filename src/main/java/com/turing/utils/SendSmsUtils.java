package com.turing.utils;

import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月19日 18:10:49
 */
@Slf4j
public class SendSmsUtils {

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId,
                                                                  String accessKeySecret) throws Exception {
        log.info("accessKeyId:{}", accessKeyId);
        log.info("accessKeySecret:{}", accessKeySecret);
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

}
