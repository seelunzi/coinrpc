package com.example.demo;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.example.demo.service.BtcHotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private BtcHotService btcService;

    @Test
    public void contextLoads() {
//		KeyPair pair = SecureUtil.generateKeyPair("RSA");
//		PrivateKey aPrivate = pair.getPrivate();
//		PublicKey aPublic = pair.getPublic();
//		aPrivate.getEncoded();
//		String encode = Base64.encode(aPrivate.getEncoded());
//		String encode1 = Base64.encode(aPublic.getEncoded());
//		System.out.println("111111111111111111111111"+encode);
//		System.out.println("222222222222222222"+encode1);
        String privateKRY = "MIICdgIBADANBgkqhkiG9w0BAQEFAA" +
                "SCAmAwggJcAgEAAoGBAN43xrvFYdgL7iycgncLilUwmBpSX" +
                "PlemJu94zCxWEXhD64kP2NAgT4EzmlluzGr4hdGcHQFR03hty4Ako1" +
                "OUfm1ZK+4IjvIki1Ipf4+2JvDUcNIR6T7RDvJSuiKDKTXXVuvXuaWOGpz37vY" +
                "M2mPFXV/SKIseb9Rg3vzS9bgIbEZAgMBAAECgYAHHfw0eV7n34HyVdTr4Se+" +
                "EL9eYHLv3bK/kjZbkeKE4m9xIWISWLeK9/Zwc7yqG5ozKqNhY+Xjva3zQDtxYSfI61" +
                "RmR3OMDnNX611CZma/UW7+dchY0QQeDyNFTK4dNfFGh2Lers8EkCKsgSqpEWPrWkBu0" +
                "u36/XkPRbgrKmh/gQJBAPvL/tmelbuMHVOLzA6QQ2aS6Q7qZtfyKNUyNs71DlTjjQYbJNbGRq" +
                "1wtcWNTS1tNwaO/O/CIJYg3Z9A7rdyp8kCQQDh7WF9VMDC0k4SeTvdc60aub4zKuvEzrX8l/ha" +
                "cnaro9Oz8uMVOOTS2FJRrvxMjyarAAJAJ/Ul+YR/xgMK2AbRAkB8L9Gi/V82H9X69bEP4cEM" +
                "XxrJO3y7tvjCpcB4dE68OXq9bzfNIf+L3VIZfCZ7aClCd9VyXtl5kf4cZIcYPVlZAkEA0vutuS82" +
                "vDlNc28H+uyV0Yl4F6yTdL1dsj/xS/Xs4KkjgL7oVd9FwakGZwW004IzqClf8K3f/9Cebfxqx" +
                "uuqoQJAefZLf0PC3erKVlhqgN1zwTky3k4wX4FduPEFilAidcbY7uHfBds9QyBghYsCswvWNqQA7w" +
                "kVY0gzNTFC978o5A==";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeN8a7xWHYC+4snIJ3C4pVMJgaUlz5XpibveMwsVhF4Q+uJD9jQIE+BM5pZbsxq+IXRnB0BUdN4bcuAJKNTlH5tWSvuCI7yJItSKX+Ptibw1HDSEek+0Q7" +
                "yUroigyk111br17mljhqc9+72DNpjxV1f0iiLHm/UYN780vW4CGxGQIDAQAB";

        RSA rsa = new RSA(privateKRY, publicKey);
        String asa = rsa.encryptStr("你好", KeyType.PublicKey);
        System.out.println(asa);

        String s = rsa.decryptStr(asa, KeyType.PrivateKey);
        System.out.println(s);
    }

    @Test
    public void name() {
        String data = "[{currency:XRP,value:10295.99981}]";
        data.replace("XRP", "1");
        data.replace("]", "1");
        System.out.println(data);
    }

    @Test
    public void Bithot() {
        String nihao = btcService.createNewAddress(null);
        System.out.print(nihao);
    }

    @Test
    public void ethr() {

    }
}
