package com.zyf.excel;

import com.zyf.i.Cursor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class ExcelReader implements Cursor {
    private File mFile;
    private ArrayList<ArrayList<Object>> mData;
    private int mCurrentRowIndex;

    public static void createFromFile(final File file, int sheetIndex, final ExcelReader.OnExcelLoadListener listener) throws FileNotFoundException {
        if (file != null && file.exists() && file.isFile()) {
            (new Thread("LoadExcelThread") {
                public void run() {
                    ExcelReader reader = new ExcelReader(file, sheetIndex);
                    if (listener != null) {
                        if (reader != null) {
                            listener.onLoad(reader);
                        } else {
                            listener.onLoad(null);
                        }
                    }
                }
            }).start();
        } else {
            throw new FileNotFoundException("文件：" + file.getAbsolutePath() + "找不到！");
        }
    }

    public ExcelReader(File file) {
        this(file, 0);
    }

    public ExcelReader(File file, int sheetIndex) {
        this.mFile = file;
        mData = ExcelUtil.readExcel(mFile, sheetIndex);
        moveToPrevious();
    }

    public File getFile() {
        return mFile;
    }

    public ArrayList<ArrayList<Object>> getData() {
        return mData;
    }

    @Override
    public int getRowCount() {
        return mData.size();
    }

    @Override
    public int getColumnCount() {
        return mData.get(0).size();
    }

    @Override
    public boolean moveToPrevious() {
        mCurrentRowIndex = -1;
        return mData != null && mData.size() > 0;
    }

    @Override
    public String getStringByIndex(int i) {
        String result = "";
        try {
            result = (String) mData.get(mCurrentRowIndex).get(i);
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<Object> getCurrentRow(){
        return mData.get(mCurrentRowIndex);
    }

    @Override
    public boolean moveToFirst() {
        mCurrentRowIndex = 0;
        return mData != null && mData.size() > 0;
    }

    @Override
    public boolean moveToNext() {
        boolean hasNext = false;
        if (mData != null && mData.size() > 0 && mCurrentRowIndex < getRowCount() - 1) {
            mCurrentRowIndex++;
            hasNext = true;
        }
        return hasNext;
    }

    @Override
    public boolean moveToPosition(int position) {
        mCurrentRowIndex = position;
        return mData != null && mData.size() > 0;
    }

    @Override
    public void close() {
        mData = null;
        System.gc();
    }

    public interface OnExcelLoadListener {
        void onLoad(ExcelReader excelReader);
    }
}