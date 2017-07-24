package com.zyf.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class ExcelReader {
    private File mFile;
    private ArrayList<ArrayList<Object>> mData;
    private ArrayList<Object> mCurrentList;
    private Iterator<ArrayList<Object>> mCurrentIterator;

    public static ExcelReader createFromFile(File file) throws FileNotFoundException {
        if(file != null && file.exists() && file.isFile()) {
            return new ExcelReader(file);
        } else {
            throw new FileNotFoundException("文件：" + file.getAbsolutePath() + "找不到！");
        }
    }

    public static void createFromFile(final File file, final ExcelReader.OnExcelLoadListener listener) throws FileNotFoundException {
        if(file != null && file.exists() && file.isFile()) {
            (new Thread("LoadExcelThread") {
                public void run() {
                    try {
                        ExcelReader reader = ExcelReader.createFromFile(file);
                        if(listener != null) {
                            if(reader != null) {
                                listener.onLoad(reader);
                            } else {
                                listener.onLoad(null);
                            }
                        }
                    } catch (FileNotFoundException var2) {
                        if(listener != null) {
                            listener.onLoad(null);
                        }
                    }

                }
            }).start();
        } else {
            throw new FileNotFoundException("文件：" + file.getAbsolutePath() + "找不到！");
        }
    }

    private ExcelReader(File file) {
        this.mFile = file;
        initData();
    }

    public File getFile() {
        return mFile;
    }

    private void initData() {
        mData = ExcelUtil.readExcel(mFile);
        mCurrentIterator = mData.iterator();
    }

    public boolean next() {
        boolean hasNext = this.mCurrentIterator.hasNext();
        if(hasNext) {
            this.mCurrentList = (ArrayList)mCurrentIterator.next();
        }

        return hasNext;
    }

    public String getStringByIndex(int i) {
        String s = (String)this.mCurrentList.get(i);
        return s;
    }

    public interface OnExcelLoadListener {
        void onLoad(ExcelReader var1);
    }
}