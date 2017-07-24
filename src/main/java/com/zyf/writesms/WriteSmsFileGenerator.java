package com.zyf.writesms;

import com.zyf.excel.ExcelReader;

import java.io.*;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class WriteSmsFileGenerator {
    private static final String SPLIT = "$;$";
    private static final String DEFAULT_TIME = "2016-12-05 10:24:03";
    private ExcelReader mReader;
    private WriteSmsFileGenerator.Skiper mSkiper;
    private int mPortIndex = -1;
    private int mContentIndex = -1;
    private String mTime = "2016-12-05 10:24:03";
    private File mTargetFile;

    private WriteSmsFileGenerator(ExcelReader reader) {
        this.mReader = reader;
    }

    public static WriteSmsFileGenerator create(ExcelReader reader) {
        return new WriteSmsFileGenerator(reader);
    }

    public WriteSmsFileGenerator setSkiper(WriteSmsFileGenerator.Skiper skiper) {
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
        if (this.mTargetFile == null) {
            File excelFile = this.mReader.getFile();
            File parentFile = excelFile.getParentFile();
            String excelFileName = excelFile.getName();
            String suffix = excelFileName.substring(excelFileName.lastIndexOf(".") + 1);
            String targetFileName = "writeSmsFrom-" + excelFileName.replace(suffix, "") + ".txt";
            this.mTargetFile = new File(parentFile, targetFileName);
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
            fos.close();
        } catch (IOException var5) {
        }

        return count;
    }

    public int printResult() {
        int count = this.printResult(new WriteSmsFileGenerator.Printer() {
            public void print(String string) {
                System.out.println(string);
            }
        });
        return count;
    }

    public int printResult(WriteSmsFileGenerator.Printer printer) {
        int count = 0;
        if (this.mReader != null) {
            while (this.mReader.next()) {
                try {
                    String e = this.mReader.getStringByIndex(this.mPortIndex);
                    String content = this.mReader.getStringByIndex(this.mContentIndex);
                    if (this.mSkiper == null || !this.mSkiper.isNeedSkip(e, content)) {
                        printer.print(e + "$;$" + content + "$;$" + this.mTime);
                        ++count;
                    }
                } catch (Exception var5) {
                    System.out.println(var5);
                }
            }
        }

        return count;
    }

    public interface Printer {
        void print(String string);
    }

    public interface Skiper {
        boolean isNeedSkip(String port, String content);
    }
}
