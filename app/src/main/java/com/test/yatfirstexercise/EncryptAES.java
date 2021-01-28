package com.test.yatfirstexercise;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptAES {

    public static final String TAG = "EncryptAES";

    public static byte[] generateKey(byte[] randomNumberSeed) {
        SecretKey secretKey = null;
        KeyGenerator keyGenerator;

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(randomNumberSeed);
            keyGenerator.init(256, random);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.e(TAG,("Key Length: " + secretKey.getEncoded().length));
        return secretKey.getEncoded();
    }

    public static byte[] cipher(int mode, byte[] key, byte[] data) {

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        byte[] cipherText = null;

        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(mode, secretKeySpec);
            cipherText = cipher.doFinal(data);

        } catch (NullPointerException e){
            Log.e(TAG,e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, e.getMessage());
        } catch (InvalidKeyException e) {
            Log.e(TAG, e.getMessage());
        } catch (BadPaddingException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, e.getMessage());
        }

        return cipherText;

    }

    public static byte[] encrypt(byte[] key, byte[] data) {
        return cipher(Cipher.ENCRYPT_MODE, key, data);
    }

    public static byte[] decrypt(byte[] key, byte[] data) {
        return cipher(Cipher.DECRYPT_MODE, key, data);
    }

    public static String StringEncryption(byte[] key, byte[] data) {

        return Base64.encodeToString(encrypt(key, data), Base64.DEFAULT);
    }

    public static String StringDecryption(byte[] key, String data) {

        String kkey = "length of key is " + key.length;
        Log.e(TAG, kkey);
        byte[] decodedData = Base64.decode(data, Base64.DEFAULT);

        return new String(decrypt(key, decodedData));


    }

}

