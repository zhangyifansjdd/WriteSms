package com.zyf.program;

import com.zyf.excel.ExcelReader;
import com.zyf.i.Skiper;
import com.zyf.i.SmsProcesser;
import com.zyf.writesms.WriteSmsFileGenerator;

import java.io.File;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class WriteSmsMain {
    /**
     * java -jar writesms.jar 0 0 2  G:/testfile.xlsx
     * <p>
     * 后跟4个参数  sheet索引  端口号索引  原文索引 Excel文件绝对路径
     *
     * @param args
     */
    public static void main(String[] args) {
        args = new String[]{"0", "1", "11", "G:\\SmsContet\\出一批.xlsx"};

        System.out.println("--------开始执行-------");
        System.out.println("--------读取参数-------");
        String filePath = "";
        int sheetIndex = -1;
        int portIndex = -1;
        int contentIndex = -1;
        if (args != null) {
            sheetIndex = Integer.parseInt(args[0]);
            System.out.println("sheetIndex : " + sheetIndex);
            portIndex = Integer.parseInt(args[1]);
            System.out.println("portIndex : " + portIndex);
            contentIndex = Integer.parseInt(args[2]);
            System.out.println("contentIndex : " + contentIndex);
            filePath = args[3];
            System.out.println("filePath : " + filePath);
        }


        File excelFile = new File(filePath);
        ExcelReader reader = new ExcelReader(excelFile, sheetIndex);
        System.out.println("--------开始写入文件-------");
        WriteSmsFileGenerator writeSmsFileGenerator = new WriteSmsFileGenerator(reader).
                setPortIndex(portIndex).
                setContentIndex(contentIndex).
                setSkiper(new Skiper() {
                    @Override
                    public boolean isNeedSkip(int index, String port, String content) {
                        boolean result = false;
                        if (port == null || port.length() == 0 || content == null || content.length() == 0) {
                            result = true;
                        }
                        if (i++%15!=0){
                            result=true;
                        }
                        return result;
                    }
                }).
                setSmsProcesser(new SmsProcesser() {
                    @Override
                    public String processPort(String port) {
                        if (port.contains(".")) {
                            port = port.substring(0, port.indexOf("."));
                        }
                        return port;
                    }

                    @Override
                    public String processSms(String smsContent) {
                        return smsContent.replaceAll("\n", "");
                    }
                });
        int count = writeSmsFileGenerator.writeResult2File();
        System.out.println("--写入" + +count + "------结束---------");
    }

    static int i=0;
}
