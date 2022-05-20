package com.turing.controller.user;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.http.HttpStatus;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.User;
import com.turing.service.AuthLoginService;
import com.turing.service.SendSmsService;
import com.turing.service.UserService;
import com.turing.utils.RegexUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private AuthLoginService authLoginService;

    @PostMapping("/bindMobile")
    @ApiOperation("微信登录的用户绑定手机号")
    public Result bindMobile(@RequestParam String phoneNumbers,
                             @RequestParam String code,
                             @RequestParam Long userId) {
        if(!RegexUtils.isPhoneNumbers(phoneNumbers)) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"手机号不合法,绑定失败!");
        }
        try {
            userService.bindMobile(userId,phoneNumbers, code);
        } catch(Exception e) {
            log.warn("用户[{}]绑定手机号[{}]失败,原因:[{}]",userId,phoneNumbers,e.getMessage());
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,e.getMessage());
        }
        return Result.success();
    }

    @PostMapping("/bindEmail")
    @ApiOperation("绑定邮箱")
    public Result bindEmail(@RequestParam String email,@RequestParam Long userId) {
        User user = userService.getById(userId);
        if(user == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"该用户不存在,绑定邮箱失败!");
        }
        return Result.success(userService.bindEmail(email,user));
    }

    @PostMapping("/bindWechat")
    @ApiOperation("短信注册的用户绑定微信")
    public Result bindWechat(@RequestParam String code,
                             @RequestParam String nickname,
                             @RequestParam String avatar,
                             @RequestParam Long userId) {
        User user = userService.getById(userId);
        if(user == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"不存在该用户,绑定失败!");
        }
        if(code == null || nickname == null || avatar == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"携带参数不完整,绑定失败!");
        }
        //获取用户本次登录的sessionKey和用户的openid唯一标识
        String result = authLoginService.getSessionKey(code);
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("调用微信官方接口返回结果:{}", jsonObject.toJSONString());
        String openid = jsonObject.getString("openid");
        log.info("用户唯一标识openid:{}",openid);
        String sessionKey = jsonObject.getString("session_key");
        log.info("登录会话密钥:{}",sessionKey);
        if(openid == null || sessionKey == null) {
            return Result.fail(HttpStatusCode.ERROR,"调用官方微信登录接口错误!");
        }
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setOpenid(openid);
        userService.updateById(user);
        return Result.success(user);
    }
}

