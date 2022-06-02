package com.turing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turing.common.OrderStatus;
import com.turing.entity.Machine;
import com.turing.entity.Order;
import com.turing.entity.PayLog;
import com.turing.entity.User;
import com.turing.entity.dto.OrderDto;
import com.turing.mapper.MachineMapper;
import com.turing.mapper.OrderMapper;
import com.turing.mapper.PayLogMapper;
import com.turing.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PayLogMapper payLogMapper;

    @Resource
    private MachineMapper machineMapper;

    @Override
    public Order submitOrder(User user, Machine machine, OrderDto orderDto) {
        Order order = new Order();
        order.setMachineName(machine.getName());
        order.setMachineId(machine.getId());
        order.setCount(orderDto.getCount());
        order.setDays(orderDto.getDays());
        order.setPricePerDay(machine.getPrice());
        BigDecimal totalAmount = machine.getPrice()
                                        .multiply(BigDecimal.valueOf(orderDto.getDays()))
                                        .multiply(BigDecimal.valueOf(orderDto.getCount()));
        order.setTotalAmount(totalAmount);
        order.setFreight(machine.getFreight());
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setBusinessId(machine.getBusinessId());
        order.setStatus(OrderStatus.SUBMIT_ORDER.getStatus());
        order.setCreatedTime(LocalDateTime.now());
        order.setDeliverAddress(machine.getDeliverAddress());
        order.setReceivedAddressId(orderDto.getAddressId());
        orderMapper.insert(order);
        log.info("下单成功:[{}]",order);
        machine.setStock(machine.getStock() - orderDto.getCount());
        machine.setSales(machine.getSales() + orderDto.getCount());
        machineMapper.updateById(machine);
        log.info("扣减库存和增加销量成功:[{}]",machine);

        return order;
    }

    @Override
    public Order payOrder(String payMode, Order order, BigDecimal payAmount) {
        order.setActualAmount(payAmount);
        order.setPayTime(LocalDateTime.now());
        order.setPayMode(payMode);
        order.setStatus(OrderStatus.COMPLETE_PAYMENT.getStatus());
        orderMapper.updateById(order);
        log.info("订单支付成功:[{}]",order);
        PayLog payLog = new PayLog();
        payLog.setPayAmount(payAmount);
        payLog.setPayTime(order.getPayTime());
        payLog.setPayUserId(order.getUserId());
        payLog.setBusinessId(order.getBusinessId());
        payLogMapper.insert(payLog);
        log.info("订单支付日志:[{}]",payLog);
        return order;
    }

    @Override
    public Order toTransit(Order order) {
        order.setStatus(OrderStatus.IN_TRANSIT.getStatus());
        orderMapper.updateById(order);
        log.info("订单开始发货:[{}]",order);
        return order;
    }

    @Override
    public Order toReceived(Order order) {
        order.setStartRentTime(LocalDateTime.now());
        order.setReturnDeadline(LocalDateTime.now().plusDays(order.getDays()));
        orderMapper.updateById(order);
        log.info("收获成功:[{}]",order);
        return order;
    }
}
