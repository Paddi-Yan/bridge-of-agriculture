package com.turing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@TableName("sys_machine")
@ApiModel(value = "农机", description = "")
@AllArgsConstructor
@NoArgsConstructor
public class Machine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("种类")
    private String type;

    @ApiModelProperty("机械名称")
    private String name;

    @ApiModelProperty("一日价格")
    private Long price;

    @ApiModelProperty("介绍")
    private String intro;

    @ApiModelProperty("数量")
    private Integer amount;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("发货地址")
    private String deliverAddress;

    @ApiModelProperty("销量")
    private Integer sales;

    @ApiModelProperty("发布时间")
    private LocalDateTime publishTime;


}
