package com.example.demo.controller;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.example.demo.service.BtcHotService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 * @author tangwenbo
 * */
@RestController
public class BthController {

    @Autowired
    private BtcHotService btcHotService;

    @PostMapping("/address/{username}")
    @ApiOperation("创建新的地址")
    @ApiImplicitParam(name = "username", value = "账户名", paramType = "path")
    public String createAddress(@PathVariable String username) {
        return btcHotService.createNewAddress(username);
    }

    @GetMapping("/bthBalance/{account}")
    @ApiOperation("得到账户余额")
    @ApiImplicitParam(name = "account", value = "账户名", paramType = "path")
    public double getBalance(@PathVariable String account) {
        return btcHotService.getBalance(account);
    }

    @GetMapping("/transaction")
    @ApiOperation("得到交易信息")
    public List<Bitcoin.Transaction> getTransaction(@RequestParam() String account, int count, int from) {
        return btcHotService.listTransactions(account, count, from);
    }

    @PutMapping("/sendFromByBth")
    @ApiOperation("发送交易")
    public String sendFrom(String fromAddress, String toBitcoinAddress, double amount) {
        return btcHotService.sendFrom(fromAddress, toBitcoinAddress, amount);
    }

    @GetMapping("/allBalance")
    @ApiOperation("得到总的余额")
    public double getBalance() {
        return btcHotService.getBalance();
    }

    @GetMapping("/transaction/{txId}")
    @ApiOperation("/得到某笔具体交易信息")
    @ApiImplicitParam(name = "txId", value = "交易txId", paramType = "path")
    public String getRawTransaction(@PathVariable String txId) {
        return btcHotService.getRawTransaction(txId);
    }

    @PutMapping("/send")
    public String sendToAddress(String address, double amount) {
        return btcHotService.sendToAddress(address, amount);
    }

    @GetMapping("/allAccounts")
    @ApiOperation("得到钱包节点的账户信息")
    public Map<String, Number> listaccounts() {
        return btcHotService.listaccounts();
    }

    @PutMapping("/sendFromByAccount")
    @ApiOperation("通过账户发送交易")
    public String sendFromByAccount(String account, String toBitcoinAddress, double amount) {
        return btcHotService.sendFromByAccount(account, toBitcoinAddress, amount);
    }

}
