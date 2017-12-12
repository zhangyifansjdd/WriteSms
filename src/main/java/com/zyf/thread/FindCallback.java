package com.zyf.thread;

/**
 * Created by ZhangYifan on 2017/8/15.
 */
public interface FindCallback {
    void onSuccessed(String smscontent,SmsFinder finder);

    void onFailed();

}
