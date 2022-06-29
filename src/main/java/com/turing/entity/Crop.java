package com.turing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author qds
 * @since 2022-06-29
 */
@Getter
@Setter
@TableName("sys_crop")
@ApiModel(value = "Crop对象", description = "")
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("农作物种类id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("农作物种类名称")
    private String name;


}
