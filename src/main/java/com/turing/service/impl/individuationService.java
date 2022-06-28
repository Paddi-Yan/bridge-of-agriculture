package com.turing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.Result;
import com.turing.entity.Dto.MachineDto;
import com.turing.entity.Dto.MachineIndivDto;
import com.turing.entity.Machine;
import com.turing.entity.Picture;
import com.turing.mapper.MachineMapper;
import com.turing.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class individuationService {

    @Autowired
    MachineMapper machineMapper;
    @Autowired
    PictureMapper pictureMapper;

    public Result individuation(Integer area, Integer up, Integer down
            , Integer peopleNumber, Integer time, String cropType, String typeId) {
        int c = cropType.charAt(0);
        List<Machine> machines = new ArrayList<>();
        if (c >= 97 && c <= 100) {
            machines = machineMapper.byCrop(4, 1);
        } else {
            machines = machineMapper.byCrop(6, 5);
        }
        List<MachineIndivDto> list = machines.stream().filter(a -> {
            if (a.getPrice().compareTo(BigDecimal.valueOf(up)) == 1
                    || a.getPrice().compareTo(BigDecimal.valueOf(down)) == -1) {
                return false;
            }
            Long aLong = Long.valueOf(typeId);
            if (!a.getType().equals(aLong)) return false;
            return true;
        }).map(a -> {
            MachineIndivDto machineDto = new MachineIndivDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect, area, peopleNumber);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list);
    }
}

