package com.usher.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Md5Crypt;

/**
 * @Author: Usher
 * @Description:
 */
public class MD5Utils {

    public static String getMD5Str(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(messageDigest.digest(string.getBytes()));
    }

}
