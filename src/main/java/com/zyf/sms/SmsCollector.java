package com.zyf.sms;

import com.zyf.i.Cursor;
import com.zyf.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class SmsCollector {
    private List<Cursor<String>> readSmsCursorList;

    public SmsCollector() {
        readSmsCursorList = new ArrayList<>();
    }

    public void addSmsContentFile(String path, int sheetIndex, int contentIndex) {
        readSmsCursorList.add(new SmsContentReader_Excel(path, sheetIndex, contentIndex));
    }

    public void addreadSmsCursor(Cursor<String> readSmsCursor) {
        readSmsCursorList.add(readSmsCursor);
    }

    public String getSmsContentFromModel(String model) {
        String result = "";
        int count=0;
        int fileIndex=0;
        for (Cursor<String> cursor : readSmsCursorList) {
            cursor.moveToFirst();
            fileIndex++;
            while (cursor.hasNext()) {
                String content = cursor.next();
                count++;
                if (RegexUtil.matchSmsModel(content.replaceAll(" ",""), model, true)) {
                    result = content;
                    return result;
                }
            }
        }
//        System.out.println("匹配到第"+fileIndex+"个文件，匹配了"+count+"次");
        return result;
    }


}
