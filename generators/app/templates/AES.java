<%= packageName %>

/**
 * Generated Using IoT communication framework generator (IotCom)
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.print.DocFlavor.STRING;

import org.apache.commons.codec.binary.Base64;



public class AES
{


    private static SecretKeySpec SecretKey ;
    private static byte[] Key ;
    private static String UserSecretKey = "<%= key %>";

    //Returns the secret key set by the user
    private static String GetKey(){
        return UserSecretKey;
    }

    //Generate and set AES secret key using user secret key
    public static void setKey(){

        MessageDigest sha = null;
        try {
            Key = GetKey().getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            Key = sha.digest(Key);
            Key = Arrays.copyOf(Key, 16); // use only first 128 bit
            SecretKey = new SecretKeySpec(Key, "AES");

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Accepts string
    //Encrypts and returns the encrypted string
    public static String encrypt(String strToEncrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e){
            System.out.println("Error while encrypting: "+e.toString());
        }

        return null;

    }

    //Accepts Encrypted string
    //Decrypts and returns the original string
    public static String decrypt(String strToDecrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return (new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "+e.toString());
        }

        return null;
    }

}