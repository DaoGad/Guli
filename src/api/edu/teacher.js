import request from '@/utils/request'

export default{
    getTeacherListPage(current,size,teacherQuery){
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${size}`,
            method: 'post',
            data: teacherQuery
        })
    },
    deleteTeacherId(id){
        return request({
            url: `/eduservice/teacher/delete/${id}`,
            method: 'delete'
        })
    },
    addTeacher(teacher){
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    getTeacherInfo(id){
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get'
        })
    },
    updateTeacherInfo(teacher){
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: teacher
        })
}
}
