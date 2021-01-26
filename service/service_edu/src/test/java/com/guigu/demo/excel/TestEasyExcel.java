package com.guigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "D:\\write.xlsx";
        EasyExcel.write(filename,DemoExcelData.class).sheet("学生列表").doWrite(getData());
    }

    private static List<DemoExcelData> getData(){
        List<DemoExcelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoExcelData data = new DemoExcelData();
            data.setSno(i);
            data.setSname("Lucy"+i);
            list.add(data);
        }
        return list;
    }
}
