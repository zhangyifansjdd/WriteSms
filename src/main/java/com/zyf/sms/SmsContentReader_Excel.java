package com.zyf.sms;

import com.zyf.excel.ExcelReader;
import com.zyf.i.Cursor;

import java.io.File;

/**
 * Created by ZhangYifan on 2017/7/25.
 */
public class SmsContentReader_Excel implements Cursor<String> {
    File excelFile;
    ExcelReader excelReader;
    int sheetIndex;
    int contentIndex;

    public SmsContentReader_Excel(String excelFilePath, int sheetIndex, int contentIndex) {
        this.excelFile = new File(excelFilePath);
        this.sheetIndex = sheetIndex;
        this.contentIndex = contentIndex;
        excelReader = new ExcelReader(excelFile, this.sheetIndex);
    }

    @Override
    public boolean hasNext() {
        return excelReader.hasNext();
    }

    @Override
    public String next() {
        excelReader.next();
        return excelReader.getStringByIndex(contentIndex);
    }

    @Override
    public void moveToFirst() {
        excelReader.moveToFirst();
    }
}
