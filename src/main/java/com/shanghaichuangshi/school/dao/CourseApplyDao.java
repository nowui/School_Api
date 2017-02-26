package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.cache.CourseApplyCache;
import com.shanghaichuangshi.school.model.Course;
import com.shanghaichuangshi.school.model.CourseApply;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CourseApplyDao extends Dao {

    private static final CourseApplyCache courseApplyCache = new CourseApplyCache();

    public int countByCourse_id(String course_id) {
        JMap map = JMap.create();
        map.put(CourseApply.COURSE_ID, course_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply.countByCourse_id", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public int countByUser_id(String user_id) {
        JMap map = JMap.create();
        map.put(CourseApply.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply.countByUser_id", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public int countByCourse_idAndUser_id(String course_id, String user_id) {
        JMap map = JMap.create();
        map.put(CourseApply.COURSE_ID, course_id);
        map.put(CourseApply.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply.countByCourse_idAndUser_id", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public int countOneDayByUser_idAndCourse_time(String user_id, Integer course_time) {
        JMap map = JMap.create();
        map.put(CourseApply.USER_ID, user_id);
        map.put(Course.COURSE_TIME, course_time);
        SqlPara sqlPara = Db.getSqlPara("course_apply.countOneDayByUser_idAndCourse_time", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<CourseApply> list(String user_id) {
        List<CourseApply> courseApplyList = courseApplyCache.getCourseApplyListByUser_id(user_id);

        if (courseApplyList == null) {
            JMap map = JMap.create();
            map.put(User.USER_ID, user_id);
            SqlPara sqlPara = Db.getSqlPara("course_apply.list", map);

            courseApplyList = new CourseApply().find(sqlPara.getSql(), sqlPara.getPara());

            courseApplyCache.setCourseApplyListByUser_id(courseApplyList, user_id);
        }

        return courseApplyList;
    }

    public List<CourseApply> listOrderByCourse_idAndCourse_timeAndStudent_number() {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("course_apply.listOrderByCourse_idAndCourse_timeAndStudent_number", map);

        return new CourseApply().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<CourseApply> listOrderByClazz_nameAndStudent_idAndCourse_time() {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("course_apply.listOrderByClazz_nameAndStudent_idAndCourse_time", map);

        return new CourseApply().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public CourseApply find(String course_apply_id) {
        JMap map = JMap.create();
        map.put(CourseApply.COURSE_APPLY_ID, course_apply_id);
        SqlPara sqlPara = Db.getSqlPara("course_apply.find", map);

        List<CourseApply> course_applyList = new CourseApply().find(sqlPara.getSql(), sqlPara.getPara());
        if (course_applyList.size() == 0) {
            return null;
        } else {
            return course_applyList.get(0);
        }
    }

    public boolean save(String course_id, Course course, String request_user_id) {
        String course_apply_id = Util.getRandomUUID();

        JMap map = JMap.create();
        map.put(CourseApply.COURSE_APPLY_ID, course_apply_id);
        map.put(CourseApply.COURSE_ID, course_id);
        map.put(CourseApply.USER_ID, request_user_id);
        map.put(CourseApply.SYSTEM_CREATE_USER_ID, request_user_id);
        map.put(CourseApply.SYSTEM_CREATE_TIME, new Date());
        map.put(CourseApply.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(CourseApply.SYSTEM_UPDATE_TIME, new Date());
        map.put(CourseApply.SYSTEM_STATUS, true);
        SqlPara sqlPara = Db.getSqlPara("course_apply.save", map);

        boolean result = Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;

        if (result) {
            List<CourseApply> courseApplyList = list(request_user_id);

            CourseApply course_apply = new CourseApply();
            course_apply.put(Course.COURSE_ID, course.getCourse_id());
            course_apply.put(Course.COURSE_NAME, course.getCourse_name());
            course_apply.put(Course.COURSE_TEACHER, course.getCourse_teacher());
            course_apply.put(Course.COURSE_TIME, course.getCourse_time());
            course_apply.put(Course.COURSE_APPLY_LIMIT, course.getCourse_apply_limit());
            course_apply.put(Course.COURSE_ID, course.getCourse_id());

            course_apply.setCourse_apply_id(course_apply_id);
            course_apply.setUser_id(request_user_id);
            courseApplyList.add(course_apply);

            courseApplyCache.setCourseApplyListByUser_id(courseApplyList, request_user_id);
        }

        return result;
    }

//    public boolean update(CourseApply course_apply, String request_user_id) {
//        course_apply.remove(CourseApply.SYSTEM_CREATE_USER_ID);
//        course_apply.remove(CourseApply.SYSTEM_CREATE_TIME);
//        course_apply.setSystem_update_user_id(request_user_id);
//        course_apply.setSystem_update_time(new Date());
//        course_apply.remove(CourseApply.SYSTEM_STATUS);
//
//        return course_apply.update();
//    }

    public boolean deleteByCourse_idAndUser_id(String course_id, String user_id) {
        JMap map = JMap.create();
        map.put(CourseApply.COURSE_ID, course_id);
        map.put(CourseApply.USER_ID, user_id);
        map.put(CourseApply.SYSTEM_UPDATE_USER_ID, user_id);
        map.put(CourseApply.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("course_apply.deleteByCourse_idAndUser_id", map);

        boolean result = Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;

        if (result) {
            List<CourseApply> courseApplyList = list(user_id);

            Iterator<CourseApply> iterator = courseApplyList.iterator();
            while (iterator.hasNext()) {
                CourseApply courseApply = iterator.next();
                if (courseApply.getCourse_id().equals(course_id) && courseApply.getUser_id().equals(user_id)) {
                    iterator.remove();
                }
            }

            courseApplyCache.setCourseApplyListByUser_id(courseApplyList, user_id);
        }

        return result;
    }

    public boolean deleteAll(String request_user_id) {
        JMap map = JMap.create();
        map.put(CourseApply.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(CourseApply.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("course_apply.deleteAll", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}