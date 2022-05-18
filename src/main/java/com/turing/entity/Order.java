package com.turing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@Getter
@Setter
@TableName("sys_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("机器名称")
    private String machineName;

    @ApiModelProperty("机器ID")
    private Long machineId;

    @ApiModelProperty("租赁机器数量")
    private Integer count;

    @ApiModelProperty("机器规格")
    private String specification;

    @ApiModelProperty("租赁总天数")
    private Integer days;

    @ApiModelProperty("租赁单天价格")
    private BigDecimal pricePerDay;

    @ApiModelProperty("商品总额")
    private BigDecimal totalAmount;

    @ApiModelProperty("运费")
    private BigDecimal freight;

    @ApiModelProperty("优惠")
    private BigDecimal preference;

    @ApiModelProperty("实付款")
    private BigDecimal actualAmount;

    @ApiModelProperty("下单用户ID")
    private Long userId;

    @ApiModelProperty("下单用户")
    private String username;

    @ApiModelProperty("商家ID")
    private Long businessId;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("下单时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    private String payMode;


}
