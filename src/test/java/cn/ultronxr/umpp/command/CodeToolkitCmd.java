package cn.ultronxr.umpp.command;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ultronxr.umpp.util.MD5Utils;
import cn.ultronxr.umpp.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Ultronxr
 * @date 2023/05/25 12:46:47
 * @description
 */
public class CodeToolkitCmd {

    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex("sadf"));
        System.out.println(MD5Utils.md5("sadf"));
        System.out.println(MD5Utils.md5WithSalt("sadf", "asd"));
        System.out.println(URLUtil.encode("https://www.google.com/search?q=java timer使用#L13", StandardCharsets.UTF_8));
        System.out.println(URLUtil.encodeQuery("https://www.google.com/search?q=java timer使用#L13", StandardCharsets.UTF_8));
        System.out.println(URLUtil.encodePathSegment("https://www.google.com/search?q=java timer使用#L13", StandardCharsets.UTF_8));
        System.out.println(URLUtil.encodeFragment("https://www.google.com/search?q=java timer使用#L13", StandardCharsets.UTF_8));
        System.out.println(URLUtil.encodeAll("https://www.google.com/search?q=java timer使用#L13", StandardCharsets.UTF_8));

        System.out.println(URLUtil.decode("https://t.bilibili.com/%3Ftab=video", StandardCharsets.UTF_8));
        System.out.println(UnicodeUtil.toUnicode("123abcABC./;一二三", false));
        System.out.println(StringUtils.native2Unicode("123abcABC./;一二三"));
        System.out.println(UnicodeUtil.toString("\\u0031\\u0032\\u0033\\u0061\\u0062\\u0063\\u0041\\u0042\\u0043\\u002e\\u002f\\u003b\\u4e00\\u4e8c\\u4e09"));
        System.out.println(StringUtils.unicode2Native("\\u0031\\u0032\\u0033\\u0061\\u0062\\u0063\\u0041\\u0042\\u0043\\u002e\\u002f\\u003b\\u4e00\\u4e8c\\u4e09"));
    }

}
