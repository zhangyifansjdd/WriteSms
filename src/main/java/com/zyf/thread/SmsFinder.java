package com.zyf.thread;

import com.zyf.util.RegexUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ZhangYifan on 2017/8/15.
 */
public class SmsFinder implements Runnable {
//    private List<String> contentList;
    private Map<String,String> data;
    private FindCallback callback;
    private String model;
    private String port;

    private volatile boolean start;
    private volatile boolean stop;

    public SmsFinder(FindCallback callback) {
//        contentList = new ArrayList<>();
        data=new HashMap<>();
        this.callback = callback;
    }

    @Override
    public void run() {
        stop = false;
//        while (!start) {
//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Iterator<Map.Entry<String,String>> iterator= data.entrySet().iterator();

        while (iterator.hasNext()){
            if (stop) {
                break;
            }
            Map.Entry<String,String> entry=iterator.next();
            String port=entry.getKey();
            String content=entry.getValue();
            if (port.equals(this.port)&&RegexUtil.matchSmsModel(content.replaceAll(" ", ""), model.replaceAll(" ", ""), true)) {
                callback.onSuccessed(content, this);
                break;
            }
        }
//        for (String content : contentList) {
//            if (stop) {
//                break;
//            }
//
//            if (RegexUtil.matchSmsModel(content.replaceAll(" ", ""), model.replaceAll(" ", ""), true)) {
//                callback.onSuccessed(content, this);
//                break;
//            }
//        }
        if (!stop) {
            callback.onFailed();
        }
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void addPortContent(String port,String content) {
        data.put(port,content);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    //    、*！*：*；*，*。*？*\?*（*）*【*】*\[*\]*!*:*;*,*\.*\(*\)*
    public static String replacefuhao(String origin) {
        StringBuilder builder = new StringBuilder(origin);
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
