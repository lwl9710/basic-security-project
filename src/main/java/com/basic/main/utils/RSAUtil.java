package com.basic.main.utils;

import com.basic.main.dto.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Objects;

/**
 * Author: 南天
 * Date: 2020-06-10 19:15
 * Content: RSA密钥工具
 */

public class RSAUtil {
    private RSAUtil(){}

    public static String getKeyString(Key key) {
        return new String(Base64.getEncoder().encode(key.getEncoded()));
    }

    public static String encrypt(String encryptText, PublicKey publicKey) {
        String str = null;
        Objects.requireNonNull(encryptText);
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            str = Base64.getEncoder().encodeToString(cipher.doFinal(encryptText.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String decrypt(String decryptText, PrivateKey privateKey) {
        String str = null;
        byte[] bytes = Base64.getDecoder().decode(decryptText.getBytes(StandardCharsets.UTF_8));
        Objects.requireNonNull(decryptText);
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            str = new String(cipher.doFinal(bytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void persistenceKey(Key key,OutputStream keyOutputStream) {
        try {
            keyOutputStream.write(getKeyString(key).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(keyOutputStream != null) {
                try {
                    keyOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static RSA getRSAToInputStream(InputStream publicKeyStream, InputStream privateKeyStream) {
        return new RSA(publicKeyStream, privateKeyStream);
    }
}