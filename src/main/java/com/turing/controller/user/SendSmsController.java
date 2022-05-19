package com.turing.controller.user;

import cn.hutool.http.HttpStatus;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.turing.common.HttpStatusCode;
import com.turing.common.RedisKey;
import com.turing.common.Result;
import com.turing.service.SendSmsService;
import com.turing.utils.RegexUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月19日 19:42:41
 */
@RestController
@RequestMapping("/user")
@Api(description = "验证码发送服务接口")
@Slf4j
public class SendSmsController {

    @Resource
    private SendSmsService sendSmsService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMessage")
    @ApiOperation("发送短信,验证码有效时间为15分钟,一分钟只能发送一次")
    @ResponseBody
    public Result sendMessage(@RequestParam String phoneNumbers) {
        //验证手机号是否合法
        if(!RegexUtils.isPhoneNumbers(phoneNumbers)) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"手机号不合法,发送验证码失败!");
        }
        //验证一分钟内该手机号是否重复发送过验证码
        if(redisTemplate.hasKey(RedisKey.MESSAGE_SEND_CONTROL_KEY + phoneNumbers)) {
            return Result.fail(HttpStatusCode.NO_CONTENT,"该号码一分钟内已经发送过验证码,请稍后重试!");
        }
        try {
            sendSmsService.sendMessage(phoneNumbers);
        } catch(Exception e) {
            log.warn("短信验证码发送至手机号[{}]失败",phoneNumbers);
            log.warn("失败原因:{}",e.getMessage());
            return Result.fail(HttpStatusCode.ERROR,"短信验证码发送失败,请稍后重试!["+e.getMessage()+"]");
        }
        return Result.success();
    }
}
