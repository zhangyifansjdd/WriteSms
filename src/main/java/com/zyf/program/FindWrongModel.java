package com.zyf.program;

import com.zyf.excel.ExcelReader;
import com.zyf.excel.ExcelWriter;
import com.zyf.util.RegexUtil;

import java.io.File;
import java.util.regex.Matcher;

/**
 * Created by ZhangYifan on 2017/8/22.
 */
public class FindWrongModel {
    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader(new File("G:\\SmsContet\\第四批\\第三批后未匹配出.xlsx"));
        System.out.println("读取完成，开始匹配");
        excelReader.moveToPrevious();
        String model="";
        String pattern="";
        while (excelReader.moveToNext()) {
            try {
                model=excelReader.getStringByIndex(4);
                pattern=RegexUtil.patternPreFilter(model,true);
                Matcher m = RegexUtil.matcher("1", pattern);
                boolean result = m.matches();
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("异常-->"+ model);
                excelReader.getCurrentRow().add(true);
            }
        }


        ExcelWriter.writeDataToExcelFile("G:\\SmsContet\\第四批\\第四批.xls", excelReader.getData());

    }
}
