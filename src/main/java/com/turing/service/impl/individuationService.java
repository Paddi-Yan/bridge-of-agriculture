package com.turing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.Result;
import com.turing.entity.Crop;
import com.turing.entity.Dto.MachineIndivDto;
import com.turing.entity.Machine;
import com.turing.entity.Picture;
import com.turing.entity.Type;
import com.turing.mapper.MachineMapper;
import com.turing.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class individuationService {

    @Autowired
    MachineMapper machineMapper;
    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    TypeServiceImpl typeService;

    public Result individuation(Integer area, Integer up, Integer down
            , Integer peopleNumber, Integer time, int[] cropType, int[] typeId) {

        List<Integer> ins = new ArrayList<>();

        for (int crop : cropType) {
            ins.add(crop);
        }

//        char[] cs = cropType.toCharArray();
//        for (int i = 0; i < cs.length; i++) {
////            ins.add(Integer.valueOf((int)cs[i] - 96));
//            ins.add(Integer.valueOf(cs[i]+""));
//        }


        List<Machine> machines = null;

        machines = machineMapper.byCrop(ins);

        List<MachineIndivDto> list = machines.stream().filter(a -> {
            if (a.getPrice().compareTo(BigDecimal.valueOf(up)) == 1
                    || a.getPrice().compareTo(BigDecimal.valueOf(down)) == -1) {
                return false;
            }
//            char[] chars = typeId.toCharArray();
//            for (int i = 0; i < chars.length; i++) {
//                if (a.getType().equals(Integer.valueOf(chars[i]+"").longValue())) return true;
//            }
            for (int type : typeId) {
                if (a.getType().equals(Integer.valueOf(type).longValue())) return true;
            }
            return false;
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


    public static void main(String[] args) {
        char a = 'a';
        System.out.println((int)a -96 );
    }

    public Result getMachineType(Double lng, Double lat) {

        return Result.success(typeService.list());

    }
}

