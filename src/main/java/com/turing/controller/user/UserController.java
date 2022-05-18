package com.turing.controller.user;


import com.turing.common.Result;
import com.turing.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Resource
    private UserService userService;


}

