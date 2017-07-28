package com.zyf.program;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangYifan on 2017/7/25.
 */
public class Test {
    private static Pattern pattern = Pattern.compile("\t+");

    public static void main(String[] args) {

        String port="10086.00";
        if (port.contains(".")) {
            port = port.substring(0, port.indexOf("."));
        }
        System.out.println(port);

//        System.out.println("打印"+test());


//        String url = "https://221.176.66.230:20001/api/smschannelcontent";
//        HashMap<String, String> params = new HashMap<>();
//        params.put("TmpType", "1");
//        params.put("StyleSheet", "none");
//        params.put("Opttype", "all");
//        params.put("Sourport", "10086");
//
//        try {
//            String s= HttpUtil.postByHttp(url,params);
//            System.out.println(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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

    private static String test(){
        try {
            System.out.println("try");
            return "fanhui";
        } finally {
            System.out.println("finally");
        }
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
