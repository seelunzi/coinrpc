package com.example.demo.service.impl;

import cn.hutool.http.HttpUtil;
import com.example.demo.mapper.RippleAddressMapper;
import com.example.demo.service.RippleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tangwenbo
 **/
@Service
public class RipplerServiceImpl implements RippleService {

    @Resource
    private RippleAddressMapper rippleAddress;

    /**
     * 生成ripple地址，秘钥
     ***/
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int creatAddress(String address, String secret) {
        return rippleAddress.insert(address, secret);
    }

    /**
     * 查询对应地址的秘钥
     **/
    @Override
    public String getSecretKey(String address) {
        return rippleAddress.findByAddress(address);
    }

    /***
     * 发送币到冷钱包
     * */
    public String sendToColdWallet() {
        return HttpUtil.get("");

    }

}
