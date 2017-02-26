package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.school.dao.CourseApplyDao;
import com.shanghaichuangshi.school.model.Course;
import com.shanghaichuangshi.school.model.CourseApply;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CourseApplyService extends Service {

    private static final CourseApplyDao courseApplyDao = new CourseApplyDao();

    private static final CourseApplyHistoryService courseApplyHistoryService = new CourseApplyHistoryService();

    public int countByCourse_id(String course_id) {
        return courseApplyDao.countByCourse_id(course_id);
    }

    public int countByUser_id(String user_id) {
        return courseApplyDao.countByUser_id(user_id);
    }

    public int countByCourse_idAndUser_id(String course_id, String user_id) {
        return courseApplyDao.countByCourse_idAndUser_id(course_id, user_id);
    }

    public int countOneDayByUser_idAndCourse_time(String user_id, Integer course_time) {
        return courseApplyDao.countOneDayByUser_idAndCourse_time(user_id, course_time);
    }

    public List<CourseApply> list(String user_id) {
        return courseApplyDao.list(user_id);
    }

    public List<CourseApply> listOrderByCourse_idAndCourse_timeAndStudent_number() {
        return courseApplyDao.listOrderByCourse_idAndCourse_timeAndStudent_number();
    }

    public List<CourseApply> listOrderByClazz_nameAndStudent_idAndCourse_time() {
        return courseApplyDao.listOrderByClazz_nameAndStudent_idAndCourse_time();
    }

    public CourseApply find(CourseApply course_apply) {
        return courseApplyDao.find(course_apply.getCourse_apply_id());
    }

    public boolean save(String course_id, Course course, String request_user_id) {
        boolean result = courseApplyDao.save(course_id, course, request_user_id);

        if (result) {
            courseApplyHistoryService.save(course_id, true, request_user_id);
        }

        return result;
    }

//    public boolean update(CourseApply course_apply, String request_user_id) {
//        return courseApplyDao.update(course_apply, request_user_id);
//    }

    public boolean deleteByCourse_idAndUser_id(String course_id, String user_id) {
        boolean result = courseApplyDao.deleteByCourse_idAndUser_id(course_id, user_id);

        if (result) {
            courseApplyHistoryService.save(course_id, false, user_id);
        }

        return result;
    }

    public boolean deleteAll(String request_user_id) {
        return courseApplyDao.deleteAll(request_user_id);
    }

}