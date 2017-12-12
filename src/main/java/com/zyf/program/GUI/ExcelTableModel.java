package com.zyf.program.GUI;

import com.zyf.excel.ExcelReader;

import javax.swing.table.AbstractTableModel;

/**
 * Created by ZhangYifan on 2017/8/2.
 */
public class ExcelTableModel extends AbstractTableModel {
    private ExcelReader excelReader;

    public ExcelTableModel(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    @Override
    public int getRowCount() {
        return excelReader.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return excelReader.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return excelReader.getData().get(rowIndex).get(columnIndex);
    }
}
