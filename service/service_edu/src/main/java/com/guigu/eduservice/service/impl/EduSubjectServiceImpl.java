package com.guigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guigu.eduservice.entity.EduSubject;
import com.guigu.eduservice.entity.excel.SubjectData;
import com.guigu.eduservice.entity.subject.OneSubject;
import com.guigu.eduservice.entity.subject.TwoSubject;
import com.guigu.eduservice.listener.SubjectExcelListener;
import com.guigu.eduservice.mapper.EduSubjectMapper;
import com.guigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> eduSubjectList = baseMapper.selectList(wrapper);

        //二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> eduSubjectLsit2 = baseMapper.selectList(wrapper2);

        List<OneSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < eduSubjectList.size(); i++) {
            EduSubject eduSubject = eduSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectLsit = new ArrayList<>();

            for (int j = 0; j < eduSubjectLsit2.size(); j++) {
                EduSubject eduSubject2 = eduSubjectLsit2.get(j);
                if(eduSubject2.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    twoFinalSubjectLsit.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectLsit);
        }
        return finalSubjectList;
    }
}
