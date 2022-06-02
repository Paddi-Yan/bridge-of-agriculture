package com.turing.utils;

import org.springframework.util.StringUtils;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月19日 19:39:23
 */
public class RegexUtils {
    /**
     * 是否符合手机格式
     *
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneNumbers(String phone) {
        return matches(phone, RegexPatterns.PHONE_REGEX);
    }

    /**
     * 数字、26个英文字母或者下划线组成
     *
     * @param str
     * @return
     */
    public static boolean isFileName(String str) {
        return matches(str, RegexPatterns.FILENAME_REGEX);
    }

    public static boolean isResoName(String str) {
        return matches(str, RegexPatterns.RESO_NAME_REGEX);
    }

    /**
     * 是否符合邮箱格式
     *
     * @param email 要校验的邮箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmail(String email) {
        return matches(email, RegexPatterns.EMAIL_REGEX);
    }

    private static boolean matches(String str, String regex) {
        if(StringUtils.isEmpty(str)) {
            return false;
        }
        return str.matches(regex);
    }

}
