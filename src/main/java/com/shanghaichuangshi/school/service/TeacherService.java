package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.dao.TeacherDao;
import com.shanghaichuangshi.school.model.Teacher;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.type.UserType;

import java.util.List;

public class TeacherService extends Service {

    private static final TeacherDao teacherDao = new TeacherDao();

    private static final UserService userService = new UserService();

    public int count(Teacher teacher) {
        return teacherDao.count(teacher.getTeacher_name());
    }

    public List<Teacher> list(Teacher teacher, int m, int n) {
        return teacherDao.list(teacher.getTeacher_name(), m, n);
    }

    public Teacher find(Teacher teacher) {
        return teacherDao.find(teacher.getTeacher_id());
    }

    public Teacher save(Teacher teacher, User user, String request_user_id) {
        Teacher t = teacherDao.save(teacher, request_user_id);

        String user_id = userService.saveByUser_accountAndUser_passwordAndObject_idAndUser_type(user.getUser_account(), user.getUser_password(), t.getTeacher_id(), UserType.TEACHER.getKey(), request_user_id);

        teacherDao.updateByTeacher_idAndUser_id(t.getTeacher_id(), user_id, request_user_id);

        return t;
    }

    public boolean update(Teacher teacher, User user, String request_user_id) {
        boolean result = teacherDao.update(teacher, request_user_id);

        userService.updateByObject_idAndUser_accountAndUser_type(teacher.getTeacher_id(), user.getUser_account(), UserType.TEACHER.getKey(), request_user_id);

        userService.updateByObject_idAndUser_passwordAndUser_type(teacher.getTeacher_id(), user.getUser_password(), UserType.TEACHER.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Teacher teacher, String request_user_id) {
        return teacherDao.delete(teacher.getTeacher_id(), request_user_id);
    }

}