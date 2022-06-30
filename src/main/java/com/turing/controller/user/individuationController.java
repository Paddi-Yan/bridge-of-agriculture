package com.turing.controller.user;

import com.alibaba.fastjson.JSON;
import com.turing.common.Result;
import com.turing.entity.Crop;
import com.turing.entity.Type;
import com.turing.service.CropService;
import com.turing.service.impl.CropServiceImpl;
import com.turing.service.impl.individuationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@RestController
@RequestMapping("/user/machine")
public class individuationController {
    @Autowired
    com.turing.service.impl.individuationService individuationService;

    @Autowired
    CropService cropService;

    @PostMapping("/individuation")
    @ApiOperation("个性化")
    public Result individuation(@RequestParam(value = "area", required = false) Integer area
            , @RequestParam(value = "up", required = false) Integer up
            , @RequestParam(value = "down", required = false) Integer down
            , @RequestParam(value = "peopleNumber", required = false) Integer peopleNumber
            , @RequestParam(value = "time", required = false) Integer time
            , @RequestParam(value = "cropType[]", required = false) int[] cropType
            , @RequestParam(value = "typeId[]", required = false)  int[] typeId) {
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
        return individuationService.getMachineType(lng, lat);
    }

//    public static void main(String[] args) {
//        JSON.
//    }

}
