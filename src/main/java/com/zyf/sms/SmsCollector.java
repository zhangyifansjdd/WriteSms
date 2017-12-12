package com.zyf.sms;

import com.zyf.i.SmsCursor;
import com.zyf.thread.FindCallback;
import com.zyf.thread.SmsFinder;
import com.zyf.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class SmsCollector implements FindCallback {
    private static final int threadNub = 3;

    private List<SmsContentCursor_Excel> readSmsCursorList;
    private ThreadPoolExecutor executor;
    private List<SmsFinder> smsFinderList;

    private volatile String resultContent;
    private volatile int failedNum;

    public SmsCollector() {
        readSmsCursorList = new ArrayList<>();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNub);

        smsFinderList = new ArrayList<>();
        for (int i = 0; i < threadNub; i++) {
            smsFinderList.add(new SmsFinder(this));
        }
    }

    public void addSmsContentFile(String path, int sheetIndex, int portIndex, int contentIndex) {
        readSmsCursorList.add(new SmsContentCursor_Excel(path, sheetIndex, portIndex, contentIndex));
    }

    public void prepare() {
        int index = 0;
        for (SmsCursor smsCursor : readSmsCursorList) {
            smsCursor.moveToPrevious();
            while (smsCursor.moveToNext()) {
                smsFinderList.get(index++ % threadNub).addPortContent(smsCursor.getNextPort(),smsCursor.getNextSms());
            }
        }
    }

    public String getSmsContentFromModel(String targetport,String model) {
        String result = "";
        for (SmsContentCursor_Excel cursor : readSmsCursorList) {
            cursor.moveToPrevious();
            while (cursor.moveToNext()) {
                String port=cursor.getNextPort();
                if (port.contains(".")){
                    port=port.substring(0,port.indexOf("."));
                }
                String content = cursor.getNextSms();
                if (port.equals(targetport)&&RegexUtil.matchSmsModel(content.replaceAll(" ", ""), model.replaceAll(" ", ""), true)) {
                    result = content;
                    return result;
                }
            }
        }
        return result;
    }

    public synchronized String getSmsContentFromModelMutilThread(String port,String model) {
        resultContent = "";
        failedNum = 0;

//        model = model.replaceAll("，", ",")
//                .replaceAll("。", ".")
//                .replaceAll("：", ":")
//                .replaceAll("！", "!")
//                .replaceAll("（", "(")
//                .replaceAll("）", ")");

        for (SmsFinder smsFinder : smsFinderList) {
            smsFinder.setModel(model);
            smsFinder.setPort(port);
            executor.execute(smsFinder);
        }

        try {
            SmsCollector.this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultContent;
    }

    @Override
    public synchronized void onSuccessed(String smscontent, SmsFinder finder) {
        if (resultContent.length() > 0) {
            return;
        }

        for (SmsFinder smsFinder : smsFinderList) {
            if (smsFinder != finder) {
                smsFinder.setStop(true);
            }
        }

        resultContent = smscontent;
        SmsCollector.this.notify();
    }

    @Override
    public synchronized void onFailed() {
        failedNum++;
        if (failedNum == threadNub) {
            this.notify();
        }
    }
}
