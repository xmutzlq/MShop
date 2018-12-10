package google.architecture.coremodel.util;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class HexUtil {/**
 * 二进制byte数组转十六进制byte数组
 * byte array to hex
 *
 * @param b byte array
 * @return hex string
 */
public static String byte2hex(byte[] b) {
    StringBuilder hs = new StringBuilder();
    String stmp;
    for (int i = 0; i < b.length; i++) {
        stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
        if (stmp.length() == 1) {
            hs.append("0").append(stmp);
        } else {
            hs.append(stmp);
        }
    }
    return hs.toString();
}

    /**
     * 十六进制byte数组转二进制byte数组
     * hex to byte array
     *
     * @param hex hex string
     * @return byte array
     */
    public static byte[] hex2byte(String hex)
            throws IllegalArgumentException{
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException ("invalid hex string");
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

}
