package com.guigu.eduservice.service;

import com.guigu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
