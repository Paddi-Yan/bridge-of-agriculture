package com.turing.controller.user;


import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.Address;
import com.turing.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 又蠢又笨的懒羊羊程序猿
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/add")
    public Result add(@RequestBody Address address) {
        addressService.save(address);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Address address) {
        if(address.getId() == null || address.getUserId() == null) {
            return Result.fail(HttpStatusCode.REQUEST_PARAM_ERROR, "携带参数不完整!");
        }
        addressService.updateById(address);
        return Result.success();
    }

    @GetMapping("/getByUserId/{userId}")
    public Result getByUserId(@PathVariable Long userId) {
        return Result.success(addressService.getByUserId(userId));
    }

    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(addressService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        addressService.removeById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(List<Long> ids) {
        addressService.removeByIds(ids);
        return Result.success();
    }
}

