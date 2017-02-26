package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.school.cache.CourseStudentCache;
import com.shanghaichuangshi.school.model.CourseStudent;
import com.shanghaichuangshi.school.type.CourseStudentType;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class CourseStudentDao extends Dao {

    private static final CourseStudentCache courseStudentCache = new CourseStudentCache();

    public int count(String course_id, String student_id) {
        JMap map = JMap.create();
        map.put(CourseStudent.COURSE_ID, course_id);
        map.put(CourseStudent.STUDENT_ID, student_id);
        SqlPara sqlPara = Db.getSqlPara("course_student.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    private List<CourseStudent> listWhiteOrBlack(String course_id, String course_student_type) {
        JMap map = JMap.create();
        map.put(CourseStudent.COURSE_ID, course_id);
        map.put(CourseStudent.COURSE_STUDENT_TYPE, course_student_type);
        SqlPara sqlPara = Db.getSqlPara("course_student.list", map);

        return new CourseStudent().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<CourseStudent> list(String course_id, String course_student_type) {
        if (course_student_type.equals(CourseStudentType.BLACK.getKey())) {
            List<CourseStudent> courseStudentList = courseStudentCache.getCourseStudentBlackListByCourse_id(course_id);

            if (courseStudentList == null) {
                courseStudentList = listWhiteOrBlack(course_id, course_student_type);

                courseStudentCache.setCourseStudentBlackListByCourse_id(courseStudentList, course_id);
            }

            return courseStudentList;
        } else {
            return listWhiteOrBlack(course_id, course_student_type);
        }
    }

    public CourseStudent find(String course_student_id) {
        JMap map = JMap.create();
        map.put(CourseStudent.COURSE_STUDENT_ID, course_student_id);
        SqlPara sqlPara = Db.getSqlPara("course_student.find", map);

        List<CourseStudent> course_studentList = new CourseStudent().find(sqlPara.getSql(), sqlPara.getPara());
        if (course_studentList.size() == 0) {
            return null;
        } else {
            return course_studentList.get(0);
        }
    }

    public CourseStudent save(CourseStudent course_student, String request_user_id) {
        courseStudentCache.removeCourseStudentBlackListByCourse_id(course_student.getCourse_id());

        course_student.setCourse_student_id(Util.getRandomUUID());
        course_student.setSystem_create_user_id(request_user_id);
        course_student.setSystem_create_time(new Date());
        course_student.setSystem_update_user_id(request_user_id);
        course_student.setSystem_update_time(new Date());
        course_student.setSystem_status(true);

        course_student.save();

        return course_student;
    }

    public boolean update(CourseStudent course_student, String request_user_id) {
        courseStudentCache.removeCourseStudentBlackListByCourse_id(course_student.getCourse_id());

        course_student.remove(CourseStudent.SYSTEM_CREATE_USER_ID);
        course_student.remove(CourseStudent.SYSTEM_CREATE_TIME);
        course_student.setSystem_update_user_id(request_user_id);
        course_student.setSystem_update_time(new Date());
        course_student.remove(CourseStudent.SYSTEM_STATUS);

        return course_student.update();
    }

    public boolean delete(String course_student_id, String request_user_id) {
        courseStudentCache.removeCourseStudentBlackList();

        JMap map = JMap.create();
        map.put(CourseStudent.COURSE_STUDENT_ID, course_student_id);
        map.put(CourseStudent.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(CourseStudent.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("course_student.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}