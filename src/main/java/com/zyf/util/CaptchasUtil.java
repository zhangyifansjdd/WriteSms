package com.zyf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HanChenxi on 2017/3/29.
 */

public class CaptchasUtil {

    public static String[] CPATCHAS_KEYWORD = {"激活码", "随机码","动态码", "校验码", "验证码", "确认码", "检验码", "验证代码", "激活代码",
            "校验代码", "动态代码", "检验代码", "确认代码", "短信口令", "动态密码", "交易码", "驗證碼", "激活碼", "動態碼", "校驗碼", "檢驗碼", "驗證代碼",
            "激活代碼", "校驗代碼", "確認代碼", "動態代碼", "檢驗代碼", "上网密码","请凭"};
    public static String[] KEYWORD = {"WiFi","5173","WLAN","2015","2016","2017","2018","http","wifi","WIFI","wlan","12306","10086","10010","51job","ENJOY","taobao","http","95599","Didi","12580","YOHO"};

    public static String getCaptchas(String content) {
        Pattern p = Pattern
                .compile("(?<![a-zA-Z0-9\\-\\*卡\\.尾号])([a-zA-Z0-9]{4,8})(?![a-zA-Z0-9\\.年\\-\\*])|(?<![a-zA-Z0-9\\*卡\\.尾号])([0-9]{6})(?![a-zA-Z0-9年\\-\\*])");
        for (int i = 0; i < KEYWORD.length; i++) {
            if (content.contains(KEYWORD[i])) {
                content = content.replaceAll(KEYWORD[i],"");
                break;
            }
        }
        content = content.replaceAll(" ","");
        Matcher m = p.matcher(content);
        if (m.find()) {
            System.out.println(m.group());
            return m.group(0);
        }
        return null;
    }

    public static boolean isCaptchasMessage(String content) {
        Boolean isCaptchasMessage = false;
        for (int i = 0; i < CPATCHAS_KEYWORD.length; i++) {
            if (content.contains(CPATCHAS_KEYWORD[i])) {
                isCaptchasMessage = true;
                break;
            }
        }
        return isCaptchasMessage;
    }

    public static String getCaptchasTime(String content) {
        content = content.replaceAll(" ","");
        Pattern p = Pattern
                .compile("([0-9]{1,3})(?:分钟|小时|秒)|([\\u4e00-\\u9fa5]{1})(?:分钟|小时|秒)");
        Matcher m = p.matcher(content);
        if (m.find()) {
            System.out.println(m.group());
            return m.group(0);
        }
        return null;
    }
}
