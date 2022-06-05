package com.turing.controller.user;

import com.turing.common.ES.EsService;
import com.turing.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/search")
public class ElasticsearchController {

    @Autowired
    EsService esService;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public Result importAll(){
        int i = esService.importAll();
        return Result.success(i);
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Result search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result search = esService.search(keyword, pageNum, pageSize);
        return search;
    }
}
