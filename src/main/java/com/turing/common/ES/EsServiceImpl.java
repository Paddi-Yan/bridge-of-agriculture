package com.turing.common.ES;

import com.turing.common.Result;
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
@Service
public class EsServiceImpl implements EsService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private productRepository productRepository;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchRestTemplate;


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
        return Result.success(list);
    }
}
