package com.turing.mapper;

import com.turing.common.ES.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper{
    List<Product> getAllProductList(@Param("id") Long id);



}