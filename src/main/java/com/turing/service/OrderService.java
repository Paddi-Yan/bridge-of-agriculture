package com.turing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turing.entity.Machine;
import com.turing.entity.Order;
import com.turing.entity.User;
import com.turing.entity.dto.OrderDto;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
public interface OrderService extends IService<Order> {

    Order submitOrder(User user, Machine machine, OrderDto orderDto);

    Order payOrder(String payMode, Order order, BigDecimal payAmount);

    Order toTransit(Order order);

    Order toReceived(Order order);
}
