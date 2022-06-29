package com.turing.service.impl;

import com.turing.common.Result;
import com.turing.entity.Crop;
import com.turing.mapper.CropMapper;
import com.turing.service.CropService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qds
 * @since 2022-06-29
 */
@Service
public class CropServiceImpl extends ServiceImpl<CropMapper, Crop> implements CropService {

    @Override
    public Result getCrop(Double lng, Double lat) {
        return Result.success(this.list());
    }
}
