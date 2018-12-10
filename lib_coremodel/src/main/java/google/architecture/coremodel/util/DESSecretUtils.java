package google.architecture.coremodel.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import google.architecture.coremodel.BuildConfig;

/**
 * 采用AES/CBC/PKCS7Padding加密
 * @author zlq
 */
public class DESSecretUtils {

    /*加密算法*/
    public static final String KEY_ALGORITHM="AES";
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String ALGORITHM_NOPadding = "AES/CBC/NoPadding";

    /**
     * @param data 要加密的数组（String 需要Base64Decoder 编码）
     * @return 加密后的byte数组
     * @throws Exception 找不到加密算法等
     */
    public static String AES_cbc_encrypt(String data) {
        String value = null;
        try {
            byte[] newKey = getSecKey();
            byte[] iv = getIv();
            // 补位操作(不做此操作,后台无法解密成功)
            int asc = 32 - data.length() % 32;
            char ascc = (char)asc;
            String fill = String.valueOf(new char[]{ascc});
            StringBuilder sb = new StringBuilder(data);
            for (int i = 0; i < asc; i ++) {
                sb.append(fill);
            }
            byte[] tmpData = sb.toString().getBytes("UTF-8");
            byte[] decData = AES_cbc_encrypt(tmpData, newKey, iv);
            if(decData == null) return value;
            byte[] newFill = new byte[tmpData.length];
            System.arraycopy(decData, 0, newFill, 0, newFill.length);
            value = new String(Base64Encoder.encode(newFill));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static byte[] AES_cbc_encrypt(byte[] srcData, byte[] key, byte[] iv) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        Cipher.getMaxAllowedKeyLength(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(srcData);
    }

    /**
     * @param data 要解密的数据
     * @throws Exception 找不到解密算法等
     */
    public static String AES_cbc_decrypt(String data) {
        String value = null;
        try {
            byte[] newKey = getSecKey();
            byte[] iv = getIv();
            byte[] decData = AES_cbc_decrypt(Base64.decode(data, Base64.DEFAULT), newKey, iv);
            if(decData == null) return value;
            if(decData[decData.length - 1] > 0) {
                byte[] newFill = new byte[decData.length - decData[decData.length - 1]];
                System.arraycopy(decData, 0, newFill, 0, newFill.length);
                value = new String(newFill, "UTF-8");
            } else {
                value = new String(decData, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private static byte[] AES_cbc_decrypt(byte[] encData, byte[] pwd, byte[] iv) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        Cipher.getMaxAllowedKeyLength(ALGORITHM_NOPadding);
        SecretKey keySpec = new SecretKeySpec(pwd, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NOPadding);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(encData);
    }

    private static byte[] getSecKey() throws  UnsupportedEncodingException {
        return getParamsValue(32);
    }

    private static byte[] getIv() throws  UnsupportedEncodingException {
        return getParamsValue(16);
    }

    private static byte[] getParamsValue(int lenght) throws  UnsupportedEncodingException {
        byte[] bData = Base64Decoder.decodeToBytes(BuildConfig.DES_KEY);
        byte[] newData = new byte[lenght];
        if(bData.length < lenght) {
            System.arraycopy(bData, 0, newData, 0, bData.length);
            for (int i = bData.length; i < newData.length; i ++) {
                newData[i] = '0';
            }
        } else {
            System.arraycopy(bData, 0, newData, 0, newData.length);
        }
        return newData;
    }
}
