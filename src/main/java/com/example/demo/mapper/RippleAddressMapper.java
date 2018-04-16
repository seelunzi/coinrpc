package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/***
 * @author tangwenbo
 * */
@Mapper
public interface RippleAddressMapper {

    /**
     * 保存ripple地址和私钥
     *
     * @param address    瑞波地址
     * @param privateKey 私钥
     * @return 返回插入数
     */
    @Insert("INSERT INTO ripple(address, privateKey) VALUES(#{address}, #{privateKey})")
    int insert(@Param("address") String address, @Param("privateKey") String privateKey);

    /***
     * 查找地址
     * @param   address 瑞波地址
     * @return 瑞波私钥
     * */
    @Select("select privateKey from ripple where address=#{address}")
    String findByAddress(@Param("address") String address);
}
