package controller;
import view.UserInput;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;


public class Crypt {

    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int DATA_LENGTH = 128;
    private Cipher encryptionCipher;

    /*
    public void setKey() throws Exception {
        key = generateAesKeyFromPin(SecretScribe.getPin());
    }*/

    public String encrypt(String data) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static SecretKeySpec generateAesKeyFromPin(String pin) {
        // Convert the PIN to a byte array
        byte[] pinBytes = pin.getBytes();

        // Create a new byte array to hold the padded key
        byte[] keyBytes = new byte[16];

        // Copy the PIN bytes to the key byte array, padding with zeros if necessary
        for (int i = 0; i < 16; i++) {
            if (i < pinBytes.length) {
                keyBytes[i] = pinBytes[i];
            } else {
                keyBytes[i] = 0;
            }
        }
        // Create a SecretKeySpec object from the key byte array
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        return keySpec;
    }

}
