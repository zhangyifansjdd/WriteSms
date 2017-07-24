package com.zyf;

import com.zyf.excel.ExcelReader;
import com.zyf.writesms.WriteSmsFileGenerator;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("--------开始执行-------");
        System.out.println("--------读取参数-------");
        String filePath = "";
        int portIndex = -1;
        int contentIndex = -1;
        if (args != null) {
            portIndex = Integer.parseInt(args[0]);
            System.out.println("portIndex : " + portIndex);
            contentIndex = Integer.parseInt(args[1]);
            System.out.println("contentIndex : " + contentIndex);
            filePath = args[2];
            System.out.println("filePath : " + filePath);
        }

//        File excelFile = new File("G:/1.24-2.17收集总表(1).xlsx");
        File excelFile = new File(filePath);

        try {
            ExcelReader reader = ExcelReader.createFromFile(excelFile);
            System.out.println("--------开始写入文件-------");
            WriteSmsFileGenerator writeSmsFileGenerator = WriteSmsFileGenerator.
                    create(reader).
                    setPortIndex(portIndex).
                    setContentIndex(contentIndex);
//                    setSkiper(new WriteSmsFileGenerator.Skiper() {
//                        public boolean isNeedSkip(String port, String content) {
//                            return !CaptchasUtil.isCaptchasMessage(content);
//                        }
//                    })
            int count = writeSmsFileGenerator.writeResult2File();
            System.out.println("--写入"+ + count + "------结束---------");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
