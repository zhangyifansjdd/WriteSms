package com.zyf.sms;

import com.zyf.i.SmsCursor;
import com.zyf.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class SmsCollector {
    private List<SmsCursor> readSmsCursorList;

    public SmsCollector() {
        readSmsCursorList = new ArrayList<>();
    }

    public void addSmsContentFile(String path, int sheetIndex, int contentIndex) {
        readSmsCursorList.add(new SmsContentCursor_Excel(path, sheetIndex, contentIndex));
    }

    public void addreadSmsCursor(SmsCursor readSmsCursor) {
        readSmsCursorList.add(readSmsCursor);
    }

    public String getSmsContentFromModel(String model) {
        String testModel=replacefuhao(model);
        String result = "";
        for (SmsCursor cursor : readSmsCursorList) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String content = cursor.getNextSms();
                if (RegexUtil.matchSmsModel(content.replaceAll(" ",""), model.replaceAll(" ",""), true)) {
                    result = content;
                    return result;
                }else {
//                    System.out.println(content);
                }
            }
        }
        return result;
    }
//    、*！*：*；*，*。*？*\?*（*）*【*】*\[*\]*!*:*;*,*\.*\(*\)*
    public static String replacefuhao(String origin) {
        StringBuilder builder=new StringBuilder(origin);
        origin = origin.replaceAll(" ", "");
        origin = origin.replaceAll("、", "");
        origin = origin.replaceAll("！", "");
        origin = origin.replaceAll("：", "");
        origin = origin.replaceAll("；", "");
        origin = origin.replaceAll("，", "");
        origin = origin.replaceAll("。", "");
        origin = origin.replaceAll("？", "");
        origin = origin.replaceAll("\\?", "");
        origin = origin.replaceAll("（", "");
        origin = origin.replaceAll("）", "");
        origin = origin.replaceAll("【", "");
        origin = origin.replaceAll("】", "");
        origin = origin.replaceAll("\\[", "");
        origin = origin.replaceAll("\\]", "");
        origin = origin.replaceAll("!", "");
        origin = origin.replaceAll(":", "");
        origin = origin.replaceAll(";", "");
        origin = origin.replaceAll(",", "");
        origin = origin.replaceAll("\\.", "");
        origin = origin.replaceAll("\\(", "");
        origin = origin.replaceAll("\\)", "");
        return origin;
    }
}
