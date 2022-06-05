package com.turing.common.ES;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface productRepository extends ElasticsearchRepository<Product, Long> {

    List<Product> findByNameOrIntroOrDeliverAddress(String name, String intro, String deliverAddress, Pageable page);
}