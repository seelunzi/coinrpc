package com.example.demo.service;

/***
 * @author tangwenbo
 * */
public interface RippleService {
    /**
     * 生成ripple地址，秘钥
     ***/
    int creatAddress(String address, String secret);

    /**
     * 查询对应地址的秘钥
     **/
    String getSecretKey(String address);
}
