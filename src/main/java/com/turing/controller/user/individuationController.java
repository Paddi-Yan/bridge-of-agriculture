package com.turing.controller.user;

import com.turing.common.Result;
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

    @GetMapping("/individuation")
    @ApiOperation("个性化, 作物种类输入单字符依次为：a（谷类）,b,c...")
    public Result individuation(Integer area,Integer up,Integer down
            ,Integer peopleNumber,Integer time,String cropType,String typeId) {
        return individuationService.individuation(area, up, down, peopleNumber, time, cropType, typeId);
    }
}
