package com.zyf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyuliang on 2015/9/28.
 */
public class RegexUtil {

    public static final String PLACE_HOLDER_REGULAR = "\\$\\{([^\\$\\{]*?)\\}";
    public static final String PLACE_HOLDER_REPLACE = "(.*?)";
    public static final String PLACE_HOLDER_REGULAR_GREEDY = "#\\{([^#\\{]*?)\\}";
    public static final String PLACE_HOLDER_REPLACE_GREEDY = "(.*)";
    public static final String MOBILE_PATTERN = "^(\\+86){0,1}[1][3,4,5,7,8][0-9]{9}$";
    public static final String IMAGE_PATTERN = "(http(s?):/)(/[^/]+)+\\.(?:jpg|jpeg|gif|png)";
    public static final String ONLY_PLACE_HOLDER_PATTERN = "\\$\\{[^}]*\\}";
    public static final String PLACE_HOLDER_ARRAY_REGULAR = "\\$\\{([^}]*?)\\[(.*?)\\]\\}";
    public static final String REPLACEMENT_FOR_LINE_BREAK = "~br#";

    public static boolean onlyPlaceHolder(String text) {
        return match(text, ONLY_PLACE_HOLDER_PATTERN);
    }

    public static List<String> findImageUrls(String text) {
        List<String> list = new ArrayList<String>();

        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String url = matcher.group(0);
            list.add(url);
        }

        return list;
    }

    public static boolean isMobile(String number) {
        if (number == null) {
            return false;
        }
        return match(number, MOBILE_PATTERN);
    }

    public static boolean matchSmsModel(String sms, String model, boolean convertSpecialChar) {
        return match(sms, patternPreFilter(model, convertSpecialChar));
    }

    public static List<String> findSmsModelArrayKeys(String model) {
        return findAllMatchedGroups(model, RegexUtil.PLACE_HOLDER_ARRAY_REGULAR);
    }

    public static List<String> findSmsModelKeys(String model) {
        if (model != null && model.contains("#{")) {
            model = model.replace("#{", "${");
        }
        return findAllMatchedGroups(model, PLACE_HOLDER_REGULAR);
    }

    public static String removeSpaceAndReplaceLine(String text) {
        if (text == null) return null;

        return text.replaceAll("(\\\\r\\\\n)+", "\n")
                .replaceAll("(\r\n)+", "\n")
                .replaceAll("(\\\\r)+", "\n")
                .replaceAll("\r+", "\n")
                .replaceAll("(\\\\n)+", "\n")
                .replaceAll("\n+", REPLACEMENT_FOR_LINE_BREAK)
                .replace("\\t", "")
                .replace("\t", "")
                .replace(" ", "");
    }

    public static String convertPatternChar(String pattern, boolean convertSpecialChar) {
        if (pattern == null) return null;

        // 正则表达式中的特殊符号：* . ? + $ ^ [] ( ) {} | \
        // $ { } \在短信模板中是标识符号，( | )在循环匹配时可能有特殊用途
        // 除$ { } \ ( | )外的字符均需转换
        pattern = pattern.replace("*", "\\*").
                replace(".", "\\.").
                replace("?", "\\?").
                replace("+", "\\+").
                replace("^", "\\^").
                replace("[", "\\[").
                replace("]", "\\]");
        // ( | )在指定需要转换时才转换
        if (convertSpecialChar) {
            pattern = pattern.replace("(", "\\(").
                    replace(")", "\\)").
                    replace("|", "\\|");
        }

        return pattern;
    }

    public static List<String> findAllMatchedGroups(String origin, String regular) {
        List<String> list = new ArrayList<String>();
        try {
            int lastIndex = regular.lastIndexOf(PLACE_HOLDER_REPLACE);
            if (lastIndex > -1 && lastIndex + PLACE_HOLDER_REPLACE.length() == regular.length()) {
                regular = regular.substring(0, lastIndex) + "(.*)";
            }

            Matcher m = matcher(origin, regular);
            while (m.find()) {
                if (m.groupCount() > 0) {
                    for (int i = 1; i <= m.groupCount(); i++) {
                        if (!regular.contains(m.group(i) + "|") && !regular.contains("|" + m.group(i))) {
                            list.add(m.group(i));
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static boolean match(String origin, String regular) {
//        【湖北农信】您尾号为{txt_账号}的卡号于{txt_时间}转出金额{txt_支出金额}，余额{txt_账户余额}，交易网点省清算中心。
        boolean result = false;

        try {
            Matcher m = matcher(origin, regular);
            result = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("origin:"+origin);
            System.out.println("regular:"+regular);
        }
        return result;
    }

    public static Matcher matcher(String origin, String regular) {
        Pattern p = Pattern.compile(regular, Pattern.DOTALL);
        return p.matcher(origin);
    }

    public static String patternPreFilter(String pattern, boolean convertSpecialChar) {
        pattern = convertPatternChar(pattern, convertSpecialChar);

        if (pattern == null) return null;

        while (matcher(pattern, PLACE_HOLDER_REGULAR_GREEDY).find()) {
            pattern = pattern.replaceAll(PLACE_HOLDER_REGULAR_GREEDY, PLACE_HOLDER_REPLACE_GREEDY);
        }

        while (matcher(pattern, PLACE_HOLDER_REGULAR).find()) {
            pattern = pattern.replaceAll(PLACE_HOLDER_REGULAR, PLACE_HOLDER_REPLACE);
        }

        return pattern;
    }
}
