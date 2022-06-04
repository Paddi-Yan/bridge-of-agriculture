package com.turing.controller.user;


import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.Address;
import com.turing.entity.Machine;
import com.turing.service.MachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/user/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @GetMapping("/latestPublish")
    @ApiOperation("最新发布")
    public Result latestPublish() {
        return machineService.latest();
    }

    @GetMapping("/particular")
    @ApiOperation("农机详情")
    public Result particular(@RequestParam(value = "machineId", required = false) Integer machineId) {
        return machineService.getParticular(machineId);
    }

    @GetMapping("/particularPlus")
    @ApiOperation("农机详情，展示数据")
    public Result particularPlus(@RequestParam(value = "machineId", required = false) Integer machineId) {
        return machineService.getParticularPlus(machineId);
    }

    @GetMapping("/classify")
    @ApiOperation("分类")
    public Result classify(@RequestParam(value = "typeId", required = false) String typeId) {
        return machineService.classify(Long.parseLong(typeId));
    }

}

