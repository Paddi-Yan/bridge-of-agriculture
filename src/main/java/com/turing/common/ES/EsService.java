package com.turing.common.ES;

import com.turing.common.Result;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EsService {
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    Product create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Result search(String keyword, Integer pageNum, Integer pageSize);

    Result searchToSales(String keyword);
    Result searchToS(String keyword);
    Result searchToPriceRise(String keyword);
    Result searchToPriceDecline(String keyword);
}
