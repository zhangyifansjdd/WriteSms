package com.zyf.i;


import java.io.Closeable;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public interface Cursor extends Closeable {
    int getRowCount();

    int getColumnCount();

    boolean moveToPrevious();

    boolean moveToFirst();

    boolean moveToNext();

    boolean moveToPosition(int position);

    String getStringByIndex(int index);
}
