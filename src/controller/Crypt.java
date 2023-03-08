package controller;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Crypt {
    private static final byte[] IV = {
            0x79, 0x34, 0x56, 0x78, 0x70, 0x25, 0x47, 0x12,
            0x11, 0x43, 0x29, 0x46, 0x76, 0x54, 0x32, 0x10
    };

    private static SecretKeySpec generateKey(String passcode) throws Exception {
        byte[] keyBytes = passcode.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(keyBytes);
        byte[] truncatedHash = new byte[16];
        System.arraycopy(hashBytes, 0, truncatedHash, 0, 16);
        return new SecretKeySpec(truncatedHash, "AES");
    }

    public static String encrypt(String plainText, String passcode) throws Exception {
        SecretKeySpec keySpec = generateKey(passcode);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV));
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText, String passcode) throws Exception {
        SecretKeySpec keySpec = generateKey(passcode);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
