package com.zyf.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYifan on 2017/8/4.
 */
public class ExcelWriter {
    public static void writeDataToExcelFile(String path, ArrayList<ArrayList<Object>> data) {
//        ExcelUtil.writeExcel(data, path);
        List<ArrayList<ArrayList<Object>>> arrayLists = new ArrayList<>();

        ArrayList<ArrayList<Object>> currentlist = new ArrayList<>();
        arrayLists.add(currentlist);
        while (data.size()>0){
            currentlist.add(data.remove(0));
            if (currentlist.size()>59999){
                currentlist=new ArrayList<>();
                arrayLists.add(currentlist);
            }
        }

        int count=0;
        for (int j = 0; j < arrayLists.size(); j++) {
            ArrayList<ArrayList<Object>> lists = arrayLists.get(j);
            ExcelUtil.writeExcel(lists,path.replace(".xls", j + ".xls"));
            count++;
        }
//        ExcelUtil.writeExcel(currentlist,path.replace(".xls", (count+1) + ".xls"));

    }
}
