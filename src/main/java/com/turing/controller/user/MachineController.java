package com.turing.controller.user;


import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.Address;
import com.turing.entity.Machine;
import com.turing.service.MachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "lng",value = "用户经度"),
            @ApiImplicitParam(name = "lat",value = "用户纬度")})
    public Result latestPublish(@RequestParam(value = "lng", required = false)Double lng,
                                @RequestParam(value = "lat", required = false) Double lat) {
        return machineService.latest(lng, lat);
    }

    @GetMapping("/particular")
    @ApiOperation("农机详情")
    @ApiImplicitParams(value = {
                    @ApiImplicitParam(name = "lng",value = "用户经度"),
                    @ApiImplicitParam(name = "lat",value = "用户纬度")})
    public Result particular(@RequestParam(value = "machineId", required = true) Integer machineId,
                             @RequestParam(value = "lng", required = false)Double lng,
                             @RequestParam(value = "lat", required = false) Double lat) {
        if (lng==null || lat== null) return machineService.getParticular(machineId);
        return machineService.getParticular(machineId,lng,lat);
    }

    @GetMapping("/particularPlus")
    @ApiOperation("农机详情，展示数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "lng",value = "用户经度"),
            @ApiImplicitParam(name = "lat",value = "用户纬度")})
    public Result particularPlus(@RequestParam(value = "machineId", required = true) Integer machineId,
                                 @RequestParam(value = "lng", required = false)Double lng,
                                 @RequestParam(value = "lat", required = false) Double lat) {
        if (lng==null || lat== null) return machineService.getParticularPlus(machineId);
        return machineService.getParticularPlus(machineId,lng,lat);
    }

    @GetMapping("/classify")
    @ApiOperation("分类")
    public Result classify(@RequestParam(value = "typeId", required = false) String typeId) {
        return machineService.classify(Long.parseLong(typeId));
    }

}

