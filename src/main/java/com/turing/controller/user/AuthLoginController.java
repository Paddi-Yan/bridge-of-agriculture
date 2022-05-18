package com.turing.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.turing.common.HttpStatusCode;
import com.turing.common.PassToken;
import com.turing.common.Result;
import com.turing.entity.User;
import com.turing.entity.vo.UserVo;
import com.turing.service.AuthLoginService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月18日 21:16:55
 */
@RequestMapping("/user/auth")
@RestController
@Slf4j
public class AuthLoginController {

    @Resource
    private AuthLoginService authLoginService;

    @PostMapping("/wechatLogin")
    @ApiOperation("微信一键登录-不需要认证")
    @ResponseBody
    @PassToken
    public Result wechatLogin(@RequestParam String code,
                              @RequestParam String nickname,
                              @RequestParam String avatar) {
        if(code == null || nickname == null || avatar == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"携带参数不完整,登陆失败!");
        }
        //获取用户本次登录的sessionKey和用户的openid唯一标识
        String result = authLoginService.getSessionKey(code);
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("调用微信登录凭证校验接口返回结果:{}", jsonObject.toJSONString());
        String openid = jsonObject.getString("openid");
        log.info("用户唯一标识openid:{}",openid);
        String sessionKey = jsonObject.getString("session_key");
        log.info("登录会话密钥:{}",sessionKey);
        if(openid == null || sessionKey == null) {
            return Result.fail(HttpStatusCode.ERROR,"调用官方微信登录接口错误!");
        }
        User user = null;
        try {
            user = authLoginService.wechatLogin(openid, sessionKey, nickname, avatar);
        } catch(Exception e) {
            log.warn(e.getMessage());
            return Result.fail(HttpStatusCode.ERROR,e.getMessage());
        }
        UserVo userVo = new UserVo();
        userVo.transform(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("openid", openid);
        resultMap.put("sessionKey", sessionKey);
        resultMap.put("userInfo", userVo);
        return Result.success(resultMap);
    }
}
