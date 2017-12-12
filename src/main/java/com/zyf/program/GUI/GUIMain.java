package com.zyf.program.GUI;

import com.zyf.excel.ExcelReader;

import javax.swing.*;
import java.io.File;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class GUIMain {


    public static void main(String[] args){
        JFrame jf = new JFrame("简单表格");
        ExcelReader excelReader=new ExcelReader(new File("G:\\SmsContet\\所有原文.xlsx"));
        JTable table=new JTable(new ExcelTableModel(excelReader));
        jf.add(new JScrollPane(table));
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }



}
