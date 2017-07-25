package com.zyf.program;

import com.zyf.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangYifan on 2017/7/25.
 */
public class Test {
    private static Pattern pattern = Pattern.compile("\t+");

    public static void main(String[] args) {

        String model="恭喜您${txt_状态}，请凭出票码到${txt_取票地址}。取票码:${txt_取票码}，影院:${txt_影厅}，影片:${txt_影片名称}，放映时间:${txt_观影时间}，祝您观影愉快！客服电话${txt_联系电话}【百度糯米电影】";
        String yuanwen="恭喜您已成功购票，请凭出票码到影院大厅中<万达出票机>自助取票。取票码:62980853471607，影院:海口万达国际影城(万国大都会店)，影片:鬼吹灯之寻龙诀，放映时间:2015-12-24 20:10(周四)，祝您观影愉快！客服电话4006099866【百度糯米电影】";

        boolean result= RegexUtil.matchSmsModel(yuanwen,model,true);

        System.out.println("ddddd");

//        File file = new File("G:\\SmsContet\\去重前.txt");
//        File targetFile = new File("G:\\SmsContet\\去重后.txt");
//        if (targetFile.exists()) {
//            targetFile.delete();
//        }
//        try {
//            targetFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            PrintWriter writer = new PrintWriter(new FileOutputStream(targetFile));
//            String line = "";
//            String buffer="";
//            try {
//                while ((line = reader.readLine()) != null) {
//                    String[] ss=line.split("\t");
//                    String origin=ss[0];
//                    String nonoisy=ss[1];
//                    if (!buffer.equals(nonoisy)){
//                        buffer=nonoisy;
//                        writer.println(origin);
//                    }
//
//                }
//                reader.close();
//                writer.flush();
//                writer.close();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("结束-------");

//        File file = new File("G:\\SmsContet\\新建文本文档.txt");
//        File targetFile = new File("G:\\SmsContet\\新建文本文档1.txt");
//        if (targetFile.exists()) {
//            targetFile.delete();
//        }
//        try {
//            targetFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            PrintWriter writer = new PrintWriter(new FileOutputStream(targetFile));
//            String line = "";
//            try {
//                while ((line = reader.readLine()) != null) {
////                    String s=findYuanwen(line);
////                    System.out.println(s);
//                    if (line.contains("【")) {
//                        writer.println(line);
//                    }
//                }
//                reader.close();
//                writer.flush();
//                writer.close();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("结束-------");
    }

    private static String findYuanwen(String line) {
        Matcher matcher = pattern.matcher(line);
        int index = 0;
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            index++;
            if (index == 3) {
                start = matcher.end();
            }
            if (index == 4) {
                end = matcher.end();
            }
        }
        return line.substring(start, end);
    }
}
