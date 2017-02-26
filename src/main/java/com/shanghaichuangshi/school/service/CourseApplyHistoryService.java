package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.school.dao.CourseApplyHistoryDao;
import com.shanghaichuangshi.school.model.CourseApplyHistory;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CourseApplyHistoryService extends Service {

    private static final CourseApplyHistoryDao course_apply_historyDao = new CourseApplyHistoryDao();

    public List<CourseApplyHistory> list(String user_id) {
        return course_apply_historyDao.list(user_id);
    }

//    public CourseApplyHistory find(CourseApplyHistory course_apply_history) {
//        return course_apply_historyDao.find(course_apply_history.getCourse_apply_history_id());
//    }

    public CourseApplyHistory save(String course_id, boolean course_apply_history_is_apply, String request_user_id) {
        return course_apply_historyDao.save(course_id, course_apply_history_is_apply, request_user_id);
    }

//    public boolean update(CourseApplyHistory course_apply_history, String request_user_id) {
//        return course_apply_historyDao.update(course_apply_history, request_user_id);
//    }
//
//    public boolean delete(CourseApplyHistory course_apply_history, String request_user_id) {
//        return course_apply_historyDao.delete(course_apply_history.getCourseApplyHistory_id(), request_user_id);
//    }

}