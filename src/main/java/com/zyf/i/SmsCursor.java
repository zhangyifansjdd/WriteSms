package com.zyf.i;

/**
 * Created by ZhangYifan on 2017/8/3.
 */
public interface SmsCursor extends Cursor{
    String getNextSms();

    String getNextPort();
}
