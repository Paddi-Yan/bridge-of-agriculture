package com.turing.service;

import com.turing.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
public interface AddressService extends IService<Address> {

    List<Address> getByUserId(Long userId);
}
