package com.github.chimmhuang.wechat.push.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Chimm Huang
 * @date 2022/8/22
 */
@RestController
@RequestMapping("/wechatToken")
public class WechatTokenConfigController {

    private static final String TOKEN = "wechatID:chimmhuangs";

    /**
     * 微信token验证
     *
     * @param signature 微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr|随机字符串
     */
    @GetMapping("/tokenCheck")
    public String wechatToken(String signature, String timestamp, String nonce, String echostr) {
        if (checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "fail";
    }

    /**
     * 验证签名
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">微信开发文档</a>
     *
     * @param signature 微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null && tmpStr.equals(signature);
    }

    /**
     * Byte字节转Hex
     *
     * @param b 字节
     * @return Hex
     */
    public static String byteToHex(byte b) {
        String hexString = Integer.toHexString(b & 0xFF);
        //由于十六进制是由0~9、A~F来表示1~16，所以如果Byte转换成Hex后如果是<16,就会是一个字符（比如A=10），通常是使用两个字符来表示16进制位的,
        //假如一个字符的话，遇到字符串11，这到底是1个字节，还是1和1两个字节，容易混淆，如果是补0，那么1和1补充后就是0101，11就表示纯粹的11
        if (hexString.length() < 2) {
            hexString = 0 + hexString;
        }
        return hexString;
    }

    /**
     * 字节数组转Hex
     *
     * @param bytes 字节数组
     * @return Hex
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (bytes != null && bytes.length > 0) {
            for (byte aByte : bytes) {
                String hex = byteToHex(aByte);
                sb.append(hex);
            }
        }
        return sb.toString();
    }
}
