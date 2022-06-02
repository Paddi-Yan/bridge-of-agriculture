package com.turing.service;

import com.turing.common.Result;
import com.turing.entity.Machine;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
public interface MachineService extends IService<Machine> {

    Result getParticular(Integer m_id);


    Result getParticularPlus(Integer m_id);

    Result latest();

}
