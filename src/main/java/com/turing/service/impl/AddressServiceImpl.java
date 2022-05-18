package com.turing.service.impl;

import com.turing.entity.Address;
import com.turing.mapper.AddressMapper;
import com.turing.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
