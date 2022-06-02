package com.turing.mapper;

import com.turing.entity.Dto.MachineDto;
import com.turing.entity.Machine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@Mapper
public interface MachineMapper extends BaseMapper<Machine> {

    public List<Machine> latest();


}
