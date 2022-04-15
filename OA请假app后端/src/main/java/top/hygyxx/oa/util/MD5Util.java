package top.hygyxx.oa.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    /**
     * @param source
     * @return MD5摘要
     */
    public static String md5Digest(String source){
        return DigestUtils.md5Hex(source);//MD5核心方法
    }

    /**
     * 对源数据加盐混淆后生成MD5摘要
     * @param source 源数据
     * @param salt  盐值
     * @return MD5摘要
     */
    public static String md5Digest(String source,int salt){
        char[] ca = source.toCharArray();//转字符数组
        for (int i = 0; i <ca.length ; i++) {
            ca[i] = (char) (ca[i] + salt);
        }
        String target =new String(ca);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
