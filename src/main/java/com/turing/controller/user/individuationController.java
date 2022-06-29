package com.turing.controller.user;

import com.turing.common.Result;
import com.turing.service.CropService;
import com.turing.service.impl.CropServiceImpl;
import com.turing.service.impl.individuationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/machine")
public class individuationController {
    @Autowired
    com.turing.service.impl.individuationService individuationService ;

    @Autowired
    CropService cropService;

    @GetMapping("/individuation")
    @ApiOperation("个性化")
    public Result individuation(@RequestParam(value = "area", required = false)Integer area
            ,@RequestParam(value = "up", required = false)Integer up
            ,@RequestParam(value = "down", required = false)Integer down
            ,@RequestParam(value = "peopleNumber", required = false)Integer peopleNumber
            ,@RequestParam(value = "time", required = false)Integer time
            ,@RequestParam(value = "cropType", required = false)String cropType
            ,@RequestParam(value = "typeId", required = false)String typeId) {
        return individuationService.individuation(area, up, down, peopleNumber, time, cropType, typeId);
    }

    @GetMapping("/getCropType")
    @ApiOperation("获取当地作物种类")
    public Result getCropTyop(@RequestParam(value = "lng", required = false) Double lng,
                          @RequestParam(value = "lat", required = false) Double lat) {
        return cropService.getCrop(lng, lat);
    }

    @GetMapping("/getMachineType")
    @ApiOperation("获取农机种类")
    public Result getMachineType(@RequestParam(value = "lng", required = false) Double lng,
                          @RequestParam(value = "lat", required = false) Double lat) {
        return individuationService.getMachineType(lng,lat);
    }

}
