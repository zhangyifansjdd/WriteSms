package com.zyf.sms;

import com.zyf.excel.ExcelReader;
import com.zyf.i.SmsCursor;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZhangYifan on 2017/7/25.
 */
public class SmsContentCursor_Excel implements SmsCursor {
    File excelFile;
    ExcelReader excelReader;
    int sheetIndex;
    int contentIndex;

    public SmsContentCursor_Excel(String excelFilePath, int sheetIndex, int contentIndex) {
        this.excelFile = new File(excelFilePath);
        this.sheetIndex = sheetIndex;
        this.contentIndex = contentIndex;
        excelReader = new ExcelReader(excelFile, this.sheetIndex);
    }

    @Override
    public String getNextSms() {
        return getStringByIndex(contentIndex);
    }

    @Override
    public int getRowCount() {
        return excelReader.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return excelReader.getColumnCount();
    }

    @Override
    public boolean moveToPrevious() {
        return excelReader.moveToPrevious();
    }

    @Override
    public boolean moveToFirst() {
        return excelReader.moveToFirst();
    }

    @Override
    public boolean moveToNext() {
        return excelReader.moveToNext();
    }

    @Override
    public boolean moveToPosition(int position) {
        return excelReader.moveToPosition(position);
    }

    @Override
    public String getStringByIndex(int index) {
        return excelReader.getStringByIndex(index);
    }

    @Override
    public void close() throws IOException {
        excelReader.close();
    }
}
