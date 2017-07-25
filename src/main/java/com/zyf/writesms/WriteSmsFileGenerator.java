package com.zyf.writesms;

import com.zyf.excel.ExcelReader;
import com.zyf.i.Printer;
import com.zyf.i.Skiper;

import java.io.*;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class WriteSmsFileGenerator {
    private static final String SPLIT = "$;$";
    private static final String DEFAULT_TIME = "2016-12-05 10:24:03";
    private ExcelReader mReader;
    private Skiper mSkiper;
    private int mPortIndex = -1;
    private int mContentIndex = -1;
    private String mTime = DEFAULT_TIME;
    private File mTargetFile;

    public WriteSmsFileGenerator(ExcelReader reader) {
        this.mReader = reader;
    }

    public WriteSmsFileGenerator setSkiper(Skiper skiper) {
        this.mSkiper = skiper;
        return this;
    }

    public WriteSmsFileGenerator setPortIndex(int index) {
        this.mPortIndex = index;
        return this;
    }

    public WriteSmsFileGenerator setContentIndex(int index) {
        this.mContentIndex = index;
        return this;
    }

    public WriteSmsFileGenerator setTime(String time) {
        this.mTime = time;
        return this;
    }

    public WriteSmsFileGenerator setTargetFile(File targetFile) {
        this.mTargetFile = targetFile;
        return this;
    }

    public File getTargetFile() {
        return mTargetFile;
    }

    public int writeResult2File() {
        if (mTargetFile == null) {
            File excelFile = this.mReader.getFile();
            File parentFile = excelFile.getParentFile();
            String excelFileName = excelFile.getName();
            String suffix = excelFileName.substring(excelFileName.lastIndexOf(".") + 1);
            String targetFileName = "writeSmsFrom-" + excelFileName.replace(suffix, "") + ".txt";
            mTargetFile = new File(parentFile, targetFileName);
        }
        int count = 0;

        try {
            if (mTargetFile.exists()) {
                mTargetFile.delete();
            }

            mTargetFile.createNewFile();

            final FileOutputStream fos = new FileOutputStream(mTargetFile);
            final PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"));
            count = this.printResult(new Printer() {
                @Override
                public void print(String string) {
                    writer.println((string));
                }
            });
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }

        return count;
    }

    public int printResult() {
        int count = printResult(new Printer() {
            public void print(String string) {
                System.out.println(string);
            }
        });
        return count;
    }

    public int printResult(Printer printer) {
        int count = 0;
        int index = 0;
        if (mReader != null) {
            while (mReader.hasNext()) {
                mReader.next();
                try {
                    String port = this.mReader.getStringByIndex(this.mPortIndex);
                    String content = this.mReader.getStringByIndex(this.mContentIndex);
                    if (this.mSkiper == null || !this.mSkiper.isNeedSkip(index++, port, content)) {
                        printer.print(port + SPLIT + content + SPLIT + this.mTime);
                        ++count;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        return count;
    }


}
