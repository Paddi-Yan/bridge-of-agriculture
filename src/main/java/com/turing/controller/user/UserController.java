package com.turing.controller.user;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.http.HttpStatus;
import cn.hutool.jwt.JWTUtil;
import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.User;
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

}

