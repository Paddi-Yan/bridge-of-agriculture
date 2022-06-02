package com.turing.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年06月02日 12:17:55
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @ApiModelProperty(value = "机器编号", required = true)
    private Long machineId;

    @ApiModelProperty(value = "租赁总天数", required = true)
    private Integer days;

    @ApiModelProperty(value = "租赁机器数量", required = true)
    private Integer count;

    @ApiModelProperty(value = "下单用户ID", required = true)
    private Long userId;

    @ApiModelProperty(value = "收货地址编号ID", required = true)
    private Long addressId;
}
