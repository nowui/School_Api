package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.school.cache.StudentCache;
import com.shanghaichuangshi.school.model.Student;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class StudentDao extends Dao {

    private static final StudentCache studentCache = new StudentCache();

    public int count(String student_name, String clazz_id) {
        JMap map = JMap.create();
        map.put(Student.STUDENT_NAME, student_name);
        map.put(Student.CLAZZ_ID, clazz_id);
        SqlPara sqlPara = Db.getSqlPara("student.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Student> list(String student_name, String clazz_id, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Student.STUDENT_NAME, student_name);
        map.put(Student.CLAZZ_ID, clazz_id);
        map.put(Authorization.M, m);
        map.put(Authorization.N, n);
        SqlPara sqlPara = Db.getSqlPara("student.list", map);

        return new Student().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Student find(String student_id) {
        JMap map = JMap.create();
        map.put(Student.STUDENT_ID, student_id);
        SqlPara sqlPara = Db.getSqlPara("student.find", map);

        List<Student> studentList = new Student().find(sqlPara.getSql(), sqlPara.getPara());
        if (studentList.size() == 0) {
            return null;
        } else {
            return studentList.get(0);
        }
    }

    public Student findByUser_id(String user_id) {
        Student student = studentCache.getStudentByUser_id(user_id);

        if (student == null) {
            JMap map = JMap.create();
            map.put(Student.USER_ID, user_id);
            SqlPara sqlPara = Db.getSqlPara("student.findByUser_id", map);

            List<Student> studentList = new Student().find(sqlPara.getSql(), sqlPara.getPara());
            if (studentList.size() == 0) {
                student = null;
            } else {
                student = studentList.get(0);
            }

            studentCache.setStudentByUser_id(student, user_id);
        }

        return student;
    }

    public Student save(Student student, String request_user_id) {
        student.setStudent_id(Util.getRandomUUID());
        student.setSystem_create_user_id(request_user_id);
        student.setSystem_create_time(new Date());
        student.setSystem_update_user_id(request_user_id);
        student.setSystem_update_time(new Date());
        student.setSystem_status(true);

        student.save();

        return student;
    }

    public boolean updateByStudent_idAndUser_id(String student_id, String user_id, String request_user_id) {
        studentCache.removeStudentByUser_id(user_id);

        JMap map = JMap.create();
        map.put(Student.STUDENT_ID, student_id);
        map.put(Student.USER_ID, user_id);
        map.put(Student.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Student.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("student.updateByStudent_idAndUser_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean update(Student student, String request_user_id) {
        studentCache.removeStudent();

        student.remove(Student.USER_ID);
        student.remove(Student.SYSTEM_CREATE_USER_ID);
        student.remove(Student.SYSTEM_CREATE_TIME);
        student.setSystem_update_user_id(request_user_id);
        student.setSystem_update_time(new Date());
        student.remove(Student.SYSTEM_STATUS);

        return student.update();
    }

    public boolean delete(String student_id, String request_user_id) {
        studentCache.removeStudent();

        JMap map = JMap.create();
        map.put(Student.STUDENT_ID, student_id);
        map.put(Student.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Student.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("student.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean deleteAll(String request_user_id) {
        studentCache.removeStudent();

        JMap map = JMap.create();
        map.put(Student.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Student.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("student.deleteAll", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}