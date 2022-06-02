package com.turing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turing.entity.Element;
import com.turing.mapper.ElementMapper;
import com.turing.service.ElementService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-06-02
 */
@Service
public class ElementServiceImpl extends ServiceImpl<ElementMapper, Element> implements ElementService {

}
