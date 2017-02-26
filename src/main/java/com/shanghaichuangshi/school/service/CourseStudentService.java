package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.school.dao.CourseStudentDao;
import com.shanghaichuangshi.school.model.CourseStudent;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CourseStudentService extends Service {

    private static final CourseStudentDao course_studentDao = new CourseStudentDao();

//    public int count(CourseStudent course_student) {
//        return course_studentDao.count(course_student.getCourse_id(), course_student.getStudent_id());
//    }

    public List<CourseStudent> list(String course_id, String course_student_type) {
        return course_studentDao.list(course_id, course_student_type);
    }

    public CourseStudent find(CourseStudent course_student) {
        return course_studentDao.find(course_student.getCourse_student_id());
    }

    public CourseStudent save(CourseStudent course_student, String request_user_id) {
        int count = course_studentDao.count(course_student.getCourse_id(), course_student.getStudent_id());

        if (count == 0) {
            return course_studentDao.save(course_student, request_user_id);
        }

        return null;
    }

//    public boolean update(CourseStudent course_student, String request_user_id) {
//        return course_studentDao.update(course_student, request_user_id);
//    }

    public boolean delete(CourseStudent course_student, String request_user_id) {
        return course_studentDao.delete(course_student.getCourse_student_id(), request_user_id);
    }

}