package com.turing.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.turing.common.ES.Product;
import com.turing.entity.Machine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@ApiModel(value = "推荐Dto", description = "")
@AllArgsConstructor
@NoArgsConstructor
public class MachineIndivDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("种类")
    private Long type;

    @ApiModelProperty("机械名称")
    private String name;

    @ApiModelProperty("一日价格")
    private BigDecimal price;

    @ApiModelProperty("介绍")
    private String intro;

    @ApiModelProperty("库存数量")
    private Integer stock;

    @ApiModelProperty("耕作面积")
    private Integer area;

    @ApiModelProperty("操作人数")
    private Integer peopleNumber;


    @ApiModelProperty("运费")
    private BigDecimal freight;

    private List<String> pictureList = new ArrayList<>();

    private Map<String,String> element= new HashMap<>();


    public void transform(Machine machine, List<String> pictureList,Integer area ,Integer peopleNumber){
        this.setArea(area);
        this.setPeopleNumber(peopleNumber);
        BeanUtils.copyProperties(machine,this);
        if (pictureList != null) this.pictureList.addAll(pictureList);
    }

}
