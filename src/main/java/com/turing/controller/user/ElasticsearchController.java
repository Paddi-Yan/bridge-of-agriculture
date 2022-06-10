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

    @ApiOperation(value = "搜索,销量")
    @RequestMapping(value = "/searchSales", method = RequestMethod.GET)
    @ResponseBody
    public Result searchSales(@RequestParam(required = false) String keyword) {
        return esService.searchToSales(keyword);
    }
    @ApiOperation(value = "搜索,新品")
    @RequestMapping(value = "/searchTime", method = RequestMethod.GET)
    @ResponseBody
    public Result searchTime(@RequestParam(required = false) String keyword) {
        return esService.searchToS(keyword);
    }

    @ApiOperation(value = "搜索,价格升序")
    @RequestMapping(value = "/searchPriceRise", method = RequestMethod.GET)
    @ResponseBody
    public Result searchPriceRise(@RequestParam(required = false) String keyword) {
        return esService.searchToPriceRise(keyword);
    }

    @ApiOperation(value = "搜索,价格降序")
    @RequestMapping(value = "/searchPriceDecline", method = RequestMethod.GET)
    @ResponseBody
    public Result searchPriceDecline(@RequestParam(required = false) String keyword) {
        return esService.searchToPriceDecline(keyword);
    }
}
