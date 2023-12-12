package com.example.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author ToTryEveryThing
 * @date 2023/12/12 14:47
 * @Description   计算哈希值
 */
public class Base64HashCalculator {

    public static String calculateBase64Hash(String base64Data) {
        try {
            // 创建SHA-256的MessageDigest对象
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将Base64编码的字符串转换为字节数组
            byte[] dataBytes = Base64.getDecoder().decode(base64Data);

            // 更新MessageDigest对象
            digest.update(dataBytes);

            // 计算哈希值
            byte[] hashBytes = digest.digest();

            // 将哈希值转换为Base64编码的字符串
            String base64Hash = Base64.getEncoder().encodeToString(hashBytes);

            return base64Hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}