package com.turing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@Getter
@Setter
@TableName("sys_element")
@ApiModel(value = "Element对象", description = "")
@AllArgsConstructor
@NoArgsConstructor
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
