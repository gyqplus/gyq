package com.dw.health.eportal;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: qjf
 * @Date: 2019/2/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PassWordTest {
    @Test
    public void testMD5PassWord(){
        String algorithmName="MD5";
        String password = "123456";
        // 生成盐值
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;
        SimpleHash md5Hash = new SimpleHash(algorithmName, password, salt, hashIterations);
        System.out.println(salt);
        System.out.println(md5Hash);
    }
    @Test
    public void getPassword(){
        // 生成盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println(salt);
        // 生成密码
        // 第一个参数前台加密后的密码
        // 第二个参数盐值
        String password = new Md5Hash("202cb962ac59075b964b07152d234b70", "8171369ab29d6cde74b471456d32cc04").toString();
        System.out.println(password);
    }
}
