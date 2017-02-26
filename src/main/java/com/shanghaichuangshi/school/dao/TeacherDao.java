package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.school.model.Teacher;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class TeacherDao extends Dao {

    public int count(String teacher_name) {
        JMap map = JMap.create();
        map.put(Teacher.TEACHER_NAME, teacher_name);
        SqlPara sqlPara = Db.getSqlPara("teacher.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Teacher> list(String teacher_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Teacher.TEACHER_NAME, teacher_name);
        map.put(Authorization.M, m);
        map.put(Authorization.N, n);
        SqlPara sqlPara = Db.getSqlPara("teacher.list", map);

        return new Teacher().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Teacher find(String teacher_id) {
        JMap map = JMap.create();
        map.put(Teacher.TEACHER_ID, teacher_id);
        SqlPara sqlPara = Db.getSqlPara("teacher.find", map);

        List<Teacher> teacherList = new Teacher().find(sqlPara.getSql(), sqlPara.getPara());
        if (teacherList.size() == 0) {
            return null;
        } else {
            return teacherList.get(0);
        }
    }

    public Teacher save(Teacher teacher, String request_user_id) {
        teacher.setTeacher_id(Util.getRandomUUID());
        teacher.setSystem_create_user_id(request_user_id);
        teacher.setSystem_create_time(new Date());
        teacher.setSystem_update_user_id(request_user_id);
        teacher.setSystem_update_time(new Date());
        teacher.setSystem_status(true);

        teacher.save();

        return teacher;
    }

    public boolean update(Teacher teacher, String request_user_id) {
        teacher.remove(Teacher.USER_ID);
        teacher.remove(Teacher.SYSTEM_CREATE_USER_ID);
        teacher.remove(Teacher.SYSTEM_CREATE_TIME);
        teacher.setSystem_update_user_id(request_user_id);
        teacher.setSystem_update_time(new Date());
        teacher.remove(Teacher.SYSTEM_STATUS);

        return teacher.update();
    }

    public boolean updateByTeacher_idAndUser_id(String teacher_id, String user_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Teacher.TEACHER_ID, teacher_id);
        map.put(Teacher.USER_ID, user_id);
        map.put(Teacher.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Teacher.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("teacher.updateByTeacher_idAndUser_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String teacher_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Teacher.TEACHER_ID, teacher_id);
        map.put(Teacher.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Teacher.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("teacher.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}