package com.basic.main.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * Author: 南天
 * Date: 2020-06-11 11:53
 * Content: 自定义RSA模型
 */

public class RSA {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSA(boolean isInit) {
        this(isInit, 1024);
    }

    public RSA(int keySize) {
        this(true, keySize);
    }

    private RSA(boolean isInit,int keySize) {
        if(isInit) {
            try {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                keyPairGen.initialize(keySize);
                KeyPair keyPair = keyPairGen.genKeyPair();
                this.publicKey = keyPair.getPublic();
                this.privateKey = keyPair.getPrivate();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    public RSA(InputStream publicInputStream, InputStream privateInputStream) {
        Objects.requireNonNull(publicInputStream);
        Objects.requireNonNull(privateInputStream);
        try(
                InputStreamReader publicInputStreamReader = new InputStreamReader(publicInputStream, StandardCharsets.UTF_8);
                InputStreamReader privateInputStreamReader = new InputStreamReader(privateInputStream, StandardCharsets.UTF_8);
        ) {
            String publicKeyString = readInputStreamReaderToString(publicInputStreamReader);
            String privateKeyString = readInputStreamReaderToString(privateInputStreamReader);
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString.getBytes(StandardCharsets.UTF_8))));
            this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString.getBytes(StandardCharsets.UTF_8))));
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    private static String readInputStreamReaderToString(InputStreamReader inputStreamReader) {
        StringBuilder keyStringBuilder = new StringBuilder();
        char[] chars = new char[1024];
        int len = 0;
        Objects.requireNonNull(inputStreamReader);
        try {
            while((len = inputStreamReader.read(chars)) != -1){
                keyStringBuilder.append(chars, 0, len);
            };
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return keyStringBuilder.toString();
    }

}
