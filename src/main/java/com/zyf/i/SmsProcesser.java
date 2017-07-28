package com.zyf.i;

/**
 * Created by ZhangYifan on 2017/7/28.
 */
public interface SmsProcesser {

    String processPort(String port);

    String processSms(String smsContent);
}
