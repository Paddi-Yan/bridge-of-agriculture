package com.turing.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.turing.entity.Machine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
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
@TableName("sys_machine")
@ApiModel(value = "Machine对象", description = "")
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto implements Serializable {

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

    @ApiModelProperty("距离")
    private Double distance;

    private List<String> pictureList = new ArrayList<>();

    private Map<String,String> element= new HashMap<>();

    public void transform(Machine machine,List<String> pictureList){
        //缺少距离测算
        this.distance = 1.4;

        BeanUtils.copyProperties(machine,this);
        if (pictureList != null) this.pictureList.addAll(pictureList);
    }

    public void transform(Machine machine,List<String> pictureList,Map<String,String> element){
        BeanUtils.copyProperties(machine,this);
        if (pictureList != null) this.pictureList.addAll(pictureList);
        if (element != null)this.element.putAll(element);
    }


}
