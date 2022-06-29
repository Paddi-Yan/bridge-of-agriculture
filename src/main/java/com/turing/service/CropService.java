package com.turing.service;

import com.turing.common.Result;
import com.turing.entity.Crop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qds
 * @since 2022-06-29
 */
public interface CropService extends IService<Crop> {

    public Result getCrop(Double lng ,Double lat);

}
