package com.guigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener {
    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    @Override
    public void invoke(Object data, AnalysisContext context) {
        System.out.println("****"+data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
