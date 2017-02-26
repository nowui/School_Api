package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.model.CourseApplyHistory;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class CourseApplyHistoryDao extends Dao {

    public List<CourseApplyHistory> list(String user_id) {
        JMap map = JMap.create();
        map.put(User.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply_history.list", map);

        return new CourseApplyHistory().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public CourseApplyHistory find(String course_apply_history_id) {
        JMap map = JMap.create();
        map.put(CourseApplyHistory.COURSE_APPLY_HISTORY_ID, course_apply_history_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply_history.find", map);

        List<CourseApplyHistory> course_apply_historyList = new CourseApplyHistory().find(sqlPara.getSql(), sqlPara.getPara());
        if (course_apply_historyList.size() == 0) {
            return null;
        } else {
            return course_apply_historyList.get(0);
        }
    }

    public CourseApplyHistory save(String course_id, boolean course_apply_history_is_apply, String request_user_id) {
        CourseApplyHistory course_apply_history = new CourseApplyHistory();
        course_apply_history.setCourse_apply_history_id(Util.getRandomUUID());
        course_apply_history.setCourse_id(course_id);
        course_apply_history.setUser_id(request_user_id);
        course_apply_history.setCourse_apply_history_status(course_apply_history_is_apply);
        course_apply_history.setSystem_create_user_id(request_user_id);
        course_apply_history.setSystem_create_time(new Date());
        course_apply_history.setSystem_update_user_id(request_user_id);
        course_apply_history.setSystem_update_time(new Date());
        course_apply_history.setSystem_status(true);

        course_apply_history.save();

        return course_apply_history;
    }

//    public boolean update(CourseApplyHistory course_apply_history, String request_user_id) {
//        course_apply_history.remove(CourseApplyHistory.SYSTEM_CREATE_USER_ID);
//        course_apply_history.remove(CourseApplyHistory.SYSTEM_CREATE_TIME);
//        course_apply_history.setSystem_update_user_id(request_user_id);
//        course_apply_history.setSystem_update_time(new Date());
//        course_apply_history.remove(CourseApplyHistory.SYSTEM_STATUS);
//
//        return course_apply_history.update();
//    }
//
//    public boolean delete(String course_apply_history_id, String request_user_id) {
//        JMap map = JMap.create();
//        map.put(CourseApplyHistory.COURSE_APPLY_HISTORY_ID, course_apply_history_id);
//        map.put(CourseApplyHistory.SYSTEM_UPDATE_USER_ID, request_user_id);
//        map.put(CourseApplyHistory.SYSTEM_UPDATE_TIME, new Date());
//        SqlPara sqlPara = Db.getSqlPara("course_apply_history.delete", map);
//
//        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
//    }

}