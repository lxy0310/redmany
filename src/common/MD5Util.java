package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {

    /**
     * 密码专用的MD5加密，传入的字符串会按"UTF-16LE"转化为字节数组进行编码，ram的密码加密方式的实现
     * @param str 要加密的字符串
     * @return 返回加密后的密文
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String passWordMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(str.getBytes());
            md.update(str.getBytes("UTF-16LE"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
         /*       if (i < 16)
                   buf.append("0");*/
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();

        return str;
    }






}
