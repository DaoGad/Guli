package com.guigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guigu.eduservice.entity.EduSubject;
import com.guigu.eduservice.entity.excel.SubjectData;
import com.guigu.eduservice.service.EduSubjectService;
import com.guigu.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {}

    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if(data == null){
            throw new GuliException(20001,"文件数据为空");
        }

        EduSubject existOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if(existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(data.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(),pid);
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(data.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
