package com.turing.common.ES;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.common.Result;
import com.turing.entity.Dto.MachineDto;
import com.turing.entity.Picture;
import com.turing.mapper.PictureMapper;
import com.turing.mapper.ProductMapper;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsServiceImpl implements EsService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private productRepository productRepository;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchRestTemplate;
    @Autowired
    private PictureMapper pictureMapper;


    @Override
    public int importAll() {
        List<Product> esProductList = productMapper.getAllProductList(null);
        Iterable<Product> esProductIterable = productRepository.saveAll(esProductList);
        Iterator<Product> iterator = esProductIterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product create(Long id) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public Result search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<Product> list = productRepository.findByNameOrIntroOrDeliverAddress(keyword, keyword, keyword, pageable);

        List<Object> list1 = list.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list1);
    }

    @Override
    public Result searchToSales(String keyword) {
        Pageable pageable = PageRequest.of(0, 100);
        List<Product> list = productRepository.findByNameOrIntroOrDeliverAddress(keyword, keyword, keyword, pageable);
        List<Product> collect1 = list.stream().sorted((a, b) -> b.getSales()-a.getSales()).collect(Collectors.toList());
        List<Object> list1 = collect1.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list1);
    }

    @Override
    public Result searchToS(String keyword) {
        Pageable pageable = PageRequest.of(0, 100);
        List<Product> list = productRepository.findByNameOrIntroOrDeliverAddress(keyword, keyword, keyword, pageable);
        List<Product> collect1 = list.stream().sorted((a, b) -> {
            if (a.getPublishTime().isAfter(b.getPublishTime())) return -1;
            else return 1;
        }).collect(Collectors.toList());


        List<Object> list1 = collect1.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list1);
    }

    @Override
    public Result searchToPriceRise(String keyword) {
        Pageable pageable = PageRequest.of(0, 100);
        List<Product> list = productRepository.findByNameOrIntroOrDeliverAddress(keyword, keyword, keyword, pageable);
        List<Product> collect1 = list.stream().sorted((a, b) -> (int) (a.getPrice().doubleValue() - b.getPrice().doubleValue())).collect(Collectors.toList());
        List<Object> list1 = collect1.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list1);
    }

    @Override
    public Result searchToPriceDecline(String keyword) {
        Pageable pageable = PageRequest.of(0, 100);
        List<Product> list = productRepository.findByNameOrIntroOrDeliverAddress(keyword, keyword, keyword, pageable);
        List<Product> collect1 = list.stream().sorted((a, b) -> -1*(int) (a.getPrice().doubleValue() - b.getPrice().doubleValue())).collect(Collectors.toList());
        List<Object> list1 = collect1.stream().map(a -> {
            MachineDto machineDto = new MachineDto();
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("m_id", a.getId());
            List<Picture> pictures = pictureMapper.selectList(queryWrapper);
            List<String> collect = pictures.stream().map(b -> b.getAddress()).collect(Collectors.toList());
            machineDto.transform(a, collect);
            return machineDto;
        }).collect(Collectors.toList());
        return Result.success(list1);
    }


}
