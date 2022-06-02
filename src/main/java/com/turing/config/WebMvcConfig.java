package com.turing.config;

import com.turing.interceptor.AuthenticateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年04月15日 10:30:50
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private AuthenticateInterceptor authenticateInterceptor;

    //    @Override
    //    public void addInterceptors(InterceptorRegistry registry) {
    //        registry.addInterceptor(authenticateInterceptor)
    //                .addPathPatterns("/**")
    //                .excludePathPatterns("/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**", "/v2/**");
    //    }
}
