package com.turing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import com.turing.common.RedisKey;
import com.turing.service.SendSmsService;
import com.turing.utils.SendSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月19日 19:22:21
 */
@Service
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {

    @Value("${alibaba-send-message.access-key-id}")
    private String accessKeyId;

    @Value("${alibaba-send-message.access-key-secret}")
    private String accessKeySecret;

    @Value("${alibaba-send-message.template-code}")
    private String templateCode;

    @Value("${alibaba-send-message.sign-name}")
    private String signName;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void sendMessage(String phoneNumbers) throws Exception {
        Client client = SendSmsUtils.createClient(accessKeyId, accessKeySecret);
        log.info("签名:{},模板:{}",signName,templateCode);
        String code = generateCode();
        HashMap<String, String> param = new HashMap<>();
        param.put("code",code);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phoneNumbers)
                .setTemplateParam(JSONObject.toJSONString(param));
        RuntimeOptions runtime = new RuntimeOptions();
        SendSmsResponseBody body = client.sendSmsWithOptions(sendSmsRequest, runtime).getBody();
        if(!"OK".equals(body.getCode())) {
            throw new Exception(body.getMessage());
        }
        log.info("发送验证码[{}]至手机号[{}]成功",code,phoneNumbers);
        //控制1分钟内只能发送一次验证码
        redisTemplate.opsForValue().set(RedisKey.MESSAGE_SEND_CONTROL_KEY+phoneNumbers,code,1, TimeUnit.MINUTES);
        //先删除之前已经发送过并未过期的验证码
        String oldMessageCode = RedisKey.MESSAGE_CODE_KEY + phoneNumbers;
        if(redisTemplate.hasKey(oldMessageCode)) {
            redisTemplate.delete(oldMessageCode);
        }
        //消息验证码15分钟内有效
        redisTemplate.opsForValue().set(RedisKey.MESSAGE_CODE_KEY+phoneNumbers,code,15,TimeUnit.MINUTES);
    }

    private String generateCode() {
        return (int)((Math.random()*9+1)*100000)+"";
    }
}
