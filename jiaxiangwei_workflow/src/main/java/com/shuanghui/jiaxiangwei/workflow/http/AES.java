package com.shuanghui.jiaxiangwei.workflow.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AES {
    //数据秘钥
    public static final String DATA_SECRET = "1234567890abcdef";
    //签名秘钥
    public static final String SIG_SECREET = "1234567890abcdef";
    //运营商密钥
    public static final String OPERATOR_SECRET = "1234567890abcdef";

    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
    public static final String KEY_MAC = "HmacMD5";

    // 加密
    public static String encrypt(final String sSrc, final String sKey, final String ivStr) {
        if (sKey == null) {
            LOGGER.info("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            LOGGER.info("Key长度不是16位");
            return null;
        }
        final byte[] raw = sKey.getBytes();
        final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            byte[] encrypted = new byte[0];

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            encrypted = cipher.doFinal(sSrc.getBytes());
            return new BASE64Encoder().encode(encrypted)
                    .replaceAll("\r\n", "")
                    .replaceAll("\n", "");

        } catch (final Exception e) {
            LOGGER.error("encrypt is error", e);
            return null;
        }

    }


    // 解密
    public static String decrypt(final String sSrc, final String sKey, final String ivStr) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                LOGGER.info("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                LOGGER.info("Key长度不是16位");
                return null;
            }
            final byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            final byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);

            final byte[] original = cipher.doFinal(encrypted1);
            return new String(original);

        } catch (final Exception ex) {
            LOGGER.error("decrypt is error", ex);
            return null;
        }
    }

    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
}
