package com.turing.controller.user;


import com.turing.common.HttpStatusCode;
import com.turing.common.OrderStatus;
import com.turing.common.Result;
import com.turing.entity.Machine;
import com.turing.entity.Order;
import com.turing.entity.User;
import com.turing.entity.dto.OrderDto;
import com.turing.service.MachineService;
import com.turing.service.OrderService;
import com.turing.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/user/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private MachineService machineService;

    @Resource
    private UserService userService;

    @ResponseBody
    @PostMapping("/submit")
    @ApiOperation(value = "发起订单", notes = "需要认证")
    public Result submitOrder(@RequestBody OrderDto orderDto) {
        User user = userService.getById(orderDto.getUserId());
        Machine machine = machineService.getById(orderDto.getMachineId());
        if(user == null || machine == null || orderDto.getCount() <= 0 || orderDto.getCount() > machine.getStock()) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR);
        }
        return Result.success(orderService.submitOrder(user, machine, orderDto));
    }

    @ResponseBody
    @PostMapping("/pay")
    @ApiOperation(value = "支付订单",notes = "需要认证")
    public Result payOrder(@RequestParam(name = "支付方式")String payMode,
                           @RequestParam Long orderId,
                           @RequestParam(name = "实付款") BigDecimal payAmount) {
        Order order = orderService.getById(orderId);
        if(order == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"订单不存在,支付失败!");
        }
        if(!OrderStatus.SUBMIT_ORDER.getStatus().equals(order.getStatus())) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"目前订单状态无法进行支付!");
        }
        return Result.success(orderService.payOrder(payMode, order,payAmount));
    }


    @ResponseBody
    @PostMapping("/toTransit/{orderId}")
    @ApiOperation(value = "发货")
    public Result toTransit(@PathVariable Long orderId) {
        Order order = orderService.getById(orderId);
        if(order == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"订单不存在!");
        }
        if(!OrderStatus.COMPLETE_PAYMENT.getStatus().equals(order.getStatus())) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"目前订单未支付,无法发货!");
        }
        return Result.success(orderService.toTransit(order));
    }

    @ResponseBody
    @PostMapping("/received/{orderId}")
    @ApiOperation(value = "确认收货")
    public Result received(@PathVariable Long orderId) {
        Order order = orderService.getById(orderId);
        if(order == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"订单不存在,收货失败!");
        }
        if(!OrderStatus.COMPLETE_PAYMENT.getStatus().equals(order.getStatus())) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR,"目前订单状态无法确认收货!");
        }
        return Result.success(orderService.toReceived(order));
    }

}

