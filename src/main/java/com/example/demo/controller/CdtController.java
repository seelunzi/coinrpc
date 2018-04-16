package com.example.demo.controller;

import com.example.demo.service.CdtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;

/***
 * @author tangwenbo
 * @since 1.8
 * */
@RestController
public class CdtController {

    @Autowired
    private CdtService cdtService;

    @GetMapping(value = "/cdt/{accountName}")
    @ApiOperation("得到账户余额")
    @ApiImplicitParam(name = "accountName", value = "账户名称", paramType = "path")
    public BigDecimal getBlance(@PathVariable String accountName) {
        return cdtService.getBalance(accountName);
    }

    @GetMapping("/cdt/{password}")
    @ApiOperation("解锁账户")
    @ApiImplicitParam(name = "password", value = "账户密码", paramType = "path")
    public Boolean unlockAccountName(@PathVariable String password) {
        return cdtService.unlock(password);
    }

    @GetMapping("/cdt/transfer")
    @ApiOperation("交易CDT")
    public String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo) {
        return cdtService.transfer(fromAccount, toAccount, amount, symbol, memo);
    }

    @GetMapping("/cdt/send2ColdAddress")
    @ApiOperation("发送币到冷钱包")
    public ResponseEntity send2ColdAddress(String toAddress, String amount) {
        return cdtService.send2ColdAddress(toAddress, amount);
    }

    @GetMapping("/cdt/sendFrom")
    @ApiOperation("提币操作")
    public HashMap<Boolean, String> sendFrom(String amount, String toAddress, String memo) {
        return cdtService.sendFrom(amount, toAddress, memo);
    }
}
