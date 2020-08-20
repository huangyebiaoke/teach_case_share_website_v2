package cn.madeai.teach_case_share_website_v2.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
    public static String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        BASE64Encoder base64e;
        base64e = new BASE64Encoder();
        return base64e.encode(md.digest(str.getBytes("utf-8")));
    }
}
