package com.turing.service.impl;

import com.turing.entity.Element;
import com.turing.mapper.ElementMapper;
import com.turing.service.ElementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@Service
public class ElementServiceImpl extends ServiceImpl<ElementMapper, Element> implements ElementService {

}
