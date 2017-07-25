package com.zyf.i;


import java.util.Iterator;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public interface Cursor<E> extends Iterator<E> {
    void moveToFirst();
}
