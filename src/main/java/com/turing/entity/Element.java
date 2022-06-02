package com.turing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-06-02
 */
@Getter
@Setter
@TableName("sys_element")
@ApiModel(value = "Element对象", description = "")
public class Element implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("机械id")
    private Long mId;

    @ApiModelProperty("机械元素key")
    private String ekey;

    @ApiModelProperty("机械元素value")
    private String evalue;


}
