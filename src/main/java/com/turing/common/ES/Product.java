package com.turing.common.ES;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "pms", shards = 1, replicas = 0)
public class Product implements Serializable {

    @Id
    private Long id;

    @ApiModelProperty("种类")
    private String type;

    @ApiModelProperty("机械名称")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @ApiModelProperty("一日价格")
    @Field(type = FieldType.Double)
    private Long price;

    @ApiModelProperty("商家编号")
    private Long businessId;

    @ApiModelProperty("介绍")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String intro;

    @ApiModelProperty("库存数量")
    private Integer stock;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("发货地址")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String deliverAddress;

    @ApiModelProperty("销量")
    private Integer sales;

    @ApiModelProperty("发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty("运费")
    private BigDecimal freight;
}
