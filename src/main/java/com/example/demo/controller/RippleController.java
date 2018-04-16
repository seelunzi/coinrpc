package com.example.demo.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.example.demo.service.impl.RipplerServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;


/***
 * @author tangwenbo
 * */
@RestController("/ripple")
public class RippleController {

    @Autowired
    private RipplerServiceImpl ripplerService;

    @Value("${publicKey}")
    private String publicKey;

    @Value("${fromAddress}")
    private String fromAddress;

    /**
     * 生成ripple新的地址
     **/
    @GetMapping("/newAddress")
    @ApiOperation(value = "生成新的ripple地址")
    public ResponseEntity getNewAddress() {
        String reponsData = HttpUtil.get("http://112.74.189.233:9090/signUp");
        JSONObject jsonObject = new JSONObject(reponsData);
        /**
         * 阿里规范，不写魔法值
         * */
        String status = "OK";
        String result = "result";
        if (status.equals(jsonObject.get(result))) {
            Object data = jsonObject.get("data");
            JSONObject secretAndAddress = new JSONObject(data);
            String address = (String) secretAndAddress.get("address");
            String secret = (String) secretAndAddress.get("secret");
            ripplerService.creatAddress(address, secret);
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误");
        }
    }

    /**
     * 发送交易
     */
    @PutMapping("/sendFrom")
    @ApiOperation("提币操作")
    public ResponseEntity<String> send(String toAddress, String value) {
        BigDecimal rippleValue = new BigDecimal(value);
        String balanceByAddress = getBalance(fromAddress);
        JSONObject jsonObjectResult = new JSONObject(balanceByAddress);
        String ok = "ok";
        String result = "result";
        if (ok.equals(jsonObjectResult.get(result))) {
            String data = jsonObjectResult.get("data").toString();
            String dataBy = data.substring(1, data.length() - 1);
            JSONObject jsonObject = new JSONObject(dataBy);
            BigDecimal balance = new BigDecimal((String) jsonObject.get("value"));
            if (rippleValue.compareTo(balance) < 0) {
                HashMap<String, Object> paramData = new HashMap<>(8);
                String secretKey = ripplerService.getSecretKey(fromAddress);
                paramData.put("fromAddress", fromAddress);
                paramData.put("toAddress", toAddress);
                paramData.put("secret", secretKey);
                paramData.put("value", value);
                return ResponseEntity.ok(HttpUtil.post("http://112.74.189.233:9090/Transaction", paramData));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("余额不足");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询余额失败");
    }

    /***
     * 查询address余额
     * */
    @GetMapping("/balance/{address}")
    @ApiOperation("得到钱包地址余额")
    @ApiImplicitParam(name = "address", value = "钱包地址", paramType = "path"
    )
    public String getBalance(@PathVariable String address) {
        System.out.println(address);
        HashMap<String, Object> paramdata = new HashMap<>(8);
        paramdata.put("address", address);
        return HttpUtil.post("http://112.74.189.233:9090/xrpGetBalances", paramdata);
    }
}
