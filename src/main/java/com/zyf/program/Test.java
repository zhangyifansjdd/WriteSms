package com.zyf.program;

import com.zyf.excel.ExcelReader;
import com.zyf.excel.ExcelUtil;
import com.zyf.excel.ExcelWriter;
import com.zyf.util.EditDistance;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ZhangYifan on 2017/7/25.
 */
public class Test {

    public static void main(String[] args) {
//        test123();
//        desonory1();


        int x = 0, y = 0;
        System.out.println(x++ + y);
        System.out.println(x + "    " + y);
    }

    private static void test123() {
        ArrayList<ArrayList<Object>> excel = ExcelUtil.readExcel2003(new File("G:\\WorkFiles\\2017-11\\筛选测试短信\\三遍过滤-滤重0.xls"), 0);
        ArrayList<ArrayList<Object>> newexcel = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            newexcel.clear();
            int index = 0;
            for (ArrayList<Object> arrayList : excel) {
                if (index++ % 7 == i) {
                    newexcel.add(arrayList);
                    System.out.println("添加" + index);
                }
            }
            ExcelUtil.writeExcel(newexcel, "G:\\WorkFiles\\2017-11\\筛选测试短信\\三遍过滤-滤重-拆分" + i + ".xls");
        }
        System.out.println("完成");
    }

    //1227-108收集短信原文
    private static void zhengli3() {
        ExcelReader excelReader = new ExcelReader(new File("G:\\SmsContet\\原文加端口号\\1227-108收集短信原文.xlsx"));
        excelReader.moveToPrevious();

        System.out.println("----开始-----");
        ArrayList<ArrayList<Object>> alldata = new ArrayList<>();
        ArrayList<Object> data = null;
        int i = 0;
        while (excelReader.moveToNext()) {
            String s0 = excelReader.getStringByIndex(0);
            String s1 = excelReader.getStringByIndex(1);
            if (!s1.equals("")) {
                if (data != null) {
                    alldata.add(data);
                    System.out.println((++i) + "-->" + data.get(1));
                }

                data = new ArrayList<>();
                data.add(excelReader.getStringByIndex(0));
                data.add(excelReader.getStringByIndex(1));
            } else {
                try {
                    data.set(1, data.get(1) + s0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("----写入中-----");
        ExcelWriter.writeDataToExcelFile("G:\\SmsContet\\原文加端口号\\\\1227-108收集短信原文-整理.xls", alldata);
        System.out.println("----结束-----");
    }

    //30w
    private static void zhengli2() {
        ExcelReader excelReader = new ExcelReader(new File("G:\\SmsContet\\去噪程序\\FILE\\30w.xlsx"));
        excelReader.moveToPrevious();

        System.out.println("----开始-----");
        ArrayList<ArrayList<Object>> alldata = new ArrayList<>();
        while (excelReader.moveToNext()) {

            String s0 = excelReader.getStringByIndex(0);
            String s1 = excelReader.getStringByIndex(2);

            if (s0.length() > 0 && s1.length() > 0) {
                ArrayList<Object> data = new ArrayList<>();
                try {
                    s0 = s0.substring(0, s0.indexOf('.'));
                } catch (Exception e) {

                }
                data.add(s0);
                data.add(s1);
                alldata.add(data);
            }
        }

        System.out.println("----写入中-----");
        ExcelWriter.writeDataToExcelFile("G:\\SmsContet\\去噪程序\\FILE\\30w-整理.xls", alldata);
        System.out.println("----结束-----");

    }

    //7.28-8.13收集短信原文.xlsx
    private static void zhengli1() {
        ExcelReader excelReader = new ExcelReader(new File("G:\\SmsContet\\去噪程序\\FILE\\7.28-8.13收集短信原文.xlsx"));
        excelReader.moveToPrevious();

        System.out.println("----开始-----");
        ArrayList<ArrayList<Object>> alldata = new ArrayList<>();
        ArrayList<Object> data = null;
        int i = 0;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream("G:\\SmsContet\\去噪程序\\FILE\\7.28-8.13收集短信原文-整理.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (excelReader.moveToNext()) {

            String s0 = excelReader.getStringByIndex(0);
            String s1 = excelReader.getStringByIndex(1);
            if (!s1.equals("")) {
                if (data != null) {
                    alldata.add(data);
                    System.out.println((++i) + "-->" + data.get(1));
                    String data0 = (String) data.get(0);
                    String data1 = (String) data.get(1);
                    if (data0.length() > 0 && data1.length() > 0) {
                        writer.println(data.get(0) + " " + ((String) data.get(1)).replaceAll(" ", ""));
                    }
                }

                data = new ArrayList<>();
                data.add(excelReader.getStringByIndex(1));
                data.add(excelReader.getStringByIndex(2));
            } else {
                try {
                    data.set(1, data.get(1) + s0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("----写入中-----");
        ExcelWriter.writeDataToExcelFile("G:\\SmsContet\\去噪程序\\FILE\\7.28-8.13收集短信原文-整理.xls", alldata);
        System.out.println("----结束-----");
    }

    private static void desonory() {
        ExcelReader excelReader = new ExcelReader(new File("G:\\WorkFiles\\2017-11\\筛选测试短信\\一遍过滤.xlsx"));
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        excelReader.moveToPrevious();
        String[] cache = new String[1000];
        int cacheindex = 0;

        while (excelReader.moveToNext()) {
            String quzaohou = excelReader.getStringByIndex(3);
            String port = excelReader.getStringByIndex(1);
            boolean equal = false;
            for (String s : cache) {
                if (s != null && EditDistance.similar(quzaohou, s) > 0.5D) {
                    equal = true;
                    break;
                }
            }

            if (!equal && !port.startsWith("10086")) {
                System.out.println("写入一条-->" + excelReader.getStringByIndex(2));
                cache[cacheindex++ % 1000] = quzaohou;
                data.add(excelReader.getCurrentRow());
            }
        }

        ExcelWriter.writeDataToExcelFile("G:\\WorkFiles\\2017-11\\筛选测试短信\\三遍过滤-滤重.xls", data);
        System.out.println("结束");

    }

    private static void desonory1() {
        ExcelReader excelReader = new ExcelReader(new File("G:\\WorkFiles\\2017-11\\筛选测试短信\\一遍过滤.xlsx"));
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        excelReader.moveToPrevious();
        String[] cache = new String[1000];
        int cacheindex = 0;

        while (excelReader.moveToNext()) {
            String quzaohou = excelReader.getStringByIndex(3);
            String port = excelReader.getStringByIndex(1);
            boolean equal = false;
            for (String s : cache) {
                if (port.equals(s)) {
                    equal = true;
                    break;
                }
            }

            if (!equal) {
                System.out.println("写入一条-->" + excelReader.getStringByIndex(2));
                cache[cacheindex++ % 1000] = port;
                data.add(excelReader.getCurrentRow());
            }
        }

        ExcelWriter.writeDataToExcelFile("G:\\WorkFiles\\2017-11\\筛选测试短信\\4遍过滤-端口号不重复.xls", data);
        System.out.println("结束");

    }
}
