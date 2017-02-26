package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.cache.CourseCache;
import com.shanghaichuangshi.school.model.Course;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class CourseDao extends Dao {

    private static final CourseCache courseCache = new CourseCache();

    public int count(String course_name) {
        JMap map = JMap.create();
        map.put(Course.COURSE_NAME, course_name);
        SqlPara sqlPara = Db.getSqlPara("course.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Course> list(String course_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Course.COURSE_NAME, course_name);
        map.put(Authorization.M, m);
        map.put(Authorization.N, n);
        SqlPara sqlPara = Db.getSqlPara("course.list", map);

        return new Course().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Course> listByUser_id(String user_id) {
        List<Course> courseList = courseCache.getCourseListByUser_id(user_id);

        if (courseList == null) {
            JMap map = JMap.create();
            map.put(User.USER_ID, user_id);
            SqlPara sqlPara = Db.getSqlPara("course.listByUser_id", map);
            courseList = new Course().find(sqlPara.getSql(), sqlPara.getPara());

            courseCache.setCourseListByUser_id(courseList, user_id);
        }

        return courseList;
    }

    public Course find(String course_id) {
        Course course = courseCache.getCourseByCourse_id(course_id);

        if (course == null) {
            JMap map = JMap.create();
            map.put(Course.COURSE_ID, course_id);
            SqlPara sqlPara = Db.getSqlPara("course.find", map);

            List<Course> courseList = new Course().find(sqlPara.getSql(), sqlPara.getPara());
            if (courseList.size() == 0) {
                course = null;
            } else {
                course = courseList.get(0);
            }

            courseCache.setCourseByCourse_id(course, course_id);
        }

        return course;
    }

    public Course save(Course course, String request_user_id) {
        course.setCourse_id(Util.getRandomUUID());
        course.setSystem_create_user_id(request_user_id);
        course.setSystem_create_time(new Date());
        course.setSystem_update_user_id(request_user_id);
        course.setSystem_update_time(new Date());
        course.setSystem_status(true);

        course.save();

        return course;
    }

    public boolean update(Course course, String request_user_id) {
        courseCache.removeCourseListByUser_id(course.getCourse_id());
        courseCache.removeCourseByCourse_id(course.getCourse_id());

        course.remove(Course.SYSTEM_CREATE_USER_ID);
        course.remove(Course.SYSTEM_CREATE_TIME);
        course.setSystem_update_user_id(request_user_id);
        course.setSystem_update_time(new Date());
        course.remove(Course.SYSTEM_STATUS);

        return course.update();
    }

    public boolean delete(String course_id, String request_user_id) {
        courseCache.removeCourseListByUser_id(course_id);
        courseCache.removeCourseByCourse_id(course_id);

        JMap map = JMap.create();
        map.put(Course.COURSE_ID, course_id);
        map.put(Course.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Course.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("course.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean deleteAll(String request_user_id) {
        courseCache.removeCourseList();
        courseCache.removeCourse();

        JMap map = JMap.create();
        map.put(Course.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Course.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("course.deleteAll", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}