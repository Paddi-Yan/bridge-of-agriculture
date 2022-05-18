package com.turing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年04月14日 23:25:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String name;

    public static final String USER_TYPE = "user";
    public static final String ADMIN_TYPE = "admin";
}
