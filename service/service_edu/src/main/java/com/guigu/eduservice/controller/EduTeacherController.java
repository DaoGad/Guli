package com.guigu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.commonutils.R;
import com.guigu.eduservice.entity.EduTeacher;
import com.guigu.eduservice.entity.vo.TeacherQuery;
import com.guigu.eduservice.service.EduTeacherService;
import com.guigu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-14
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag == true){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageListTeacher(/*@ApiParam(name = "currnt", value = "第几页？", required = true)*/ @PathVariable Long current,
                             /*@ApiParam(name = "szie", value = "每页展示多少？", required = true)*/ @PathVariable Long size){
        Page<EduTeacher> pageTeacher = new Page(current,size);

//        try {
//            int i = 10/0;
//        }catch (Exception e){
//            throw new GuliException(20001,"执行了GuliException异常处理！");
//        }

        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "条件分页查询讲师列表")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherConfition(@PathVariable Long current, @PathVariable Long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page(current,size);
        QueryWrapper<EduTeacher> wrapper =  new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("begin",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("end",end);
        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加讲师信息")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation(value = "根据ID更新讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

