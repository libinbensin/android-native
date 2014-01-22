package com.android.foodbook.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by libin on 12/24/13.
 *
 * class to create security
 */
public class AppSecurity
{
    private AppSecurity()
    {

    }

    public static byte[] encrypt(byte[] key , byte[] data)
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,keySpec);
            return cipher.doFinal(data);
        }
        catch (NoSuchAlgorithmException e)
        {

        }
        catch (NoSuchPaddingException e)
        {
        }
        catch (InvalidKeyException e)
        {

        }
        catch (BadPaddingException e)
        {

        }
        catch (IllegalBlockSizeException e)
        {

        }
        return null;
    }

    public static byte[] decrypt(byte[] key , byte[] encryptedData)
    {
        SecretKeySpec keySpec = new SecretKeySpec(key ,"AES");
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(encryptedData);
        }
        catch (NoSuchAlgorithmException e)
        {

        }
        catch (NoSuchPaddingException e)
        {

        }
        catch (InvalidKeyException e)
        {

        }
        catch (BadPaddingException e)
        {

        }
        catch (IllegalBlockSizeException e)
        {

        }
        return null;
    }

    /**
     * method to generate a secure key
     * @return
     */
    public static byte[] getSecretKey()
    {
        try
        {
            // create an AES algorithm instance.
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            keyGenerator.init(128,secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
    }
}
