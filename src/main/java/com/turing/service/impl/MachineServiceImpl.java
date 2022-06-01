package com.turing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.Result;
import com.turing.entity.Dto.MachineDto;
import com.turing.entity.Element;
import com.turing.entity.Machine;
import com.turing.entity.Picture;
import com.turing.mapper.ElementMapper;
import com.turing.mapper.MachineMapper;
import com.turing.mapper.PictureMapper;
import com.turing.service.ElementService;
import com.turing.service.MachineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@Service
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements MachineService {
    @Autowired
    MachineMapper machineMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    ElementMapper elementMapper;
    @Override
    public Result getParticular(Integer m_id) {
        Machine machine = machineMapper.selectById(m_id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m_id",m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine,collect);
        return Result.success(machineDto);
    }

    @Override
    public Result getParticularPlus(Integer m_id) {
        Machine machine = machineMapper.selectById(m_id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m_id",m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());

        QueryWrapper<Element> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("m_id",m_id);
        List<Element> elements = elementMapper.selectList(queryWrapper1);
        System.out.println(elements.toString());
        Map<String,String> map = new HashMap<>();
        for (Element element : elements) {
            map.put(element.getEkey(),element.getEvalue());
        }
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine,collect,map);
        return Result.success(machineDto);
    }
}
