package com.zyf.excel;

import com.zyf.i.Cursor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class ExcelReader implements Cursor<String> {
    private File mFile;
    private ArrayList<ArrayList<Object>> mData;
    private ArrayList<Object> mCurrentList;
    private Iterator<ArrayList<Object>> mCurrentIterator;

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
        moveToFirst();
    }

    public File getFile() {
        return mFile;
    }

    @Override
    public boolean hasNext() {
        return mCurrentIterator.hasNext();
    }

    @Override
    public String next() {
        mCurrentList = mCurrentIterator.next();
        return Arrays.toString(mCurrentList.toArray());
    }

    public String getStringByIndex(int i) {
        String result = "";
        try {
            result = (String) mCurrentList.get(i);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void moveToFirst() {
        mCurrentIterator = mData.iterator();
    }

    public interface OnExcelLoadListener {
        void onLoad(ExcelReader var1);
    }
}