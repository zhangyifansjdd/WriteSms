package com.zyf.program;

import com.zyf.excel.ExcelReader;
import com.zyf.writesms.WriteSmsFileGenerator;

import java.io.File;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class WriteSmsMain {
    /**
     * 可以使用命令行的方式运行
     * java -jar WriteSms-1.0-SNAPSHOT.jar 0 0 2  G:/testfile.xlsx
     * <p>
     * 后跟3个参数  端口号索引  原文索引 Excel文件绝对路径
     *
     * @param args
     */
    public static void main(String[] args) {
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

//        File excelFile = new File("G:/1.24-2.17收集总表(1).xlsx");
        File excelFile = new File(filePath);
        ExcelReader reader = new ExcelReader(excelFile, sheetIndex);
        System.out.println("--------开始写入文件-------");
        WriteSmsFileGenerator writeSmsFileGenerator = new WriteSmsFileGenerator(reader).
                setPortIndex(portIndex).
                setContentIndex(contentIndex);
//                    setSkiper(new WriteSmsFileGenerator.Skiper() {
//                        public boolean isNeedSkip(String port, String content) {
//                            return !CaptchasUtil.isCaptchasMessage(content);
//                        }
//                    })
        int count = writeSmsFileGenerator.writeResult2File();
        System.out.println("--写入" + +count + "------结束---------");
    }
}
