package com.turing.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.turing.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月18日 21:38:55
 */
@Getter
@Setter
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String nickname;

    private String mobile;

    private String openid;

    private String email;

    private String avatar;

    public void transform(User user) {
        BeanUtils.copyProperties(user,this);
    }
}
