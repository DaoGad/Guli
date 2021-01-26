package com.guigu.eduservice.service.impl;

import com.guigu.eduservice.entity.EduCourse;
import com.guigu.eduservice.entity.EduCourseDescription;
import com.guigu.eduservice.entity.vo.CourseInfoVo;
import com.guigu.eduservice.mapper.EduCourseMapper;
import com.guigu.eduservice.service.EduCourseDescriptionService;
import com.guigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int rowNum = baseMapper.insert(eduCourse);
        if(rowNum <= 0){
            throw new GuliException(20001,"课程信息添加失败");
        }

        String id = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(id);
        courseDescriptionService.save(eduCourseDescription);

        return id;
    }
}
