package com.turing.controller.user;


import com.turing.common.HttpStatusCode;
import com.turing.common.Result;
import com.turing.entity.Address;
import com.turing.entity.Machine;
import com.turing.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qds
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/turing/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @GetMapping("/particular")
    public Result particular(@RequestParam(value = "machineId",required = false) Integer machineId) {
        return machineService.getParticular(machineId);
    }

    @GetMapping("/particularPlus")
    public Result particularPlus(@RequestParam(value = "machineId",required = false) Integer machineId) {
        return machineService.getParticularPlus(machineId);
    }
}

