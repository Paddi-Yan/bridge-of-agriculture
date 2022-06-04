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
import com.turing.service.MachineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turing.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        queryWrapper.eq("m_id", m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine, collect);
        return Result.success(machineDto);
    }

    @Override
    public Result getParticularPlus(Integer m_id) {
        Machine machine = machineMapper.selectById(m_id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m_id", m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());

        QueryWrapper<Element> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("m_id", m_id);
        List<Element> elements = elementMapper.selectList(queryWrapper1);
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            map.put(element.getEkey(), element.getEvalue());
        }
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine, collect, map);
        return Result.success(machineDto);
    }

    @Override
    public Result latest() {
        List<Machine> latest = machineMapper.latest();
        List<Object> list = latest.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result classify(long typeId) {
        return Result.success(machineMapper.classify(typeId));
    }

    @Override
    public Result getParticular(Integer m_id, Double lng, Double lat) {
        Machine machine = machineMapper.selectById(m_id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m_id", m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine, collect);

        double distance = DistanceUtils.getDistance(lng, lat, machine.getLng().doubleValue(), machine.getLat().doubleValue());
        machineDto.setDistance(distance);
        return Result.success(machineDto);
    }

    @Override
    public Result getParticularPlus(Integer m_id, Double lng, Double lat) {
        Machine machine = machineMapper.selectById(m_id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("m_id", m_id);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        List<String> collect = pictures.stream().map(a -> a.getAddress()).collect(Collectors.toList());

        QueryWrapper<Element> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("m_id", m_id);
        List<Element> elements = elementMapper.selectList(queryWrapper1);
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            map.put(element.getEkey(), element.getEvalue());
        }
        MachineDto machineDto = new MachineDto();
        machineDto.transform(machine, collect, map);
        double distance = DistanceUtils.getDistance(lng, lat, machine.getLng().doubleValue(), machine.getLat().doubleValue());
        machineDto.setDistance(distance);
        return Result.success(machineDto);
    }

}
