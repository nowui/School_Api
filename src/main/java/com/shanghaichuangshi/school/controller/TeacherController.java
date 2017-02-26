package com.shanghaichuangshi.school.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.school.model.Teacher;
import com.shanghaichuangshi.school.service.TeacherService;

import java.util.List;

public class TeacherController extends Controller {

    private static final TeacherService teacherService = new TeacherService();

    @ActionKey(Url.TEACHER_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Teacher model = getParameter(Teacher.class);

        model.validate(Teacher.TEACHER_NAME);

        List<Teacher> teacherList = teacherService.list(model, getM(), getN());

        renderSuccessJson(teacherList);
    }

    @ActionKey(Url.TEACHER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Teacher model = getParameter(Teacher.class);

        model.validate(Teacher.TEACHER_NAME);

        int count = teacherService.count(model);

        List<Teacher> teacherList = teacherService.list(model, getM(), getN());

        renderSuccessJson(count, teacherList);
    }

    @ActionKey(Url.TEACHER_FIND)
    public void find() {
        Teacher model = getParameter(Teacher.class);

        model.validate(Teacher.TEACHER_ID);

        Teacher teacher = teacherService.find(model);

        teacher.removeUnfindable();

        renderSuccessJson(teacher);
    }

    @ActionKey(Url.TEACHER_ADMIN_FIND)
    public void adminFind() {
        Teacher model = getParameter(Teacher.class);

        model.validate(Teacher.TEACHER_ID);

        Teacher teacher = teacherService.find(model);

        renderSuccessJson(teacher);
    }

    @ActionKey(Url.TEACHER_SAVE)
    public void save() {
        Teacher model = getParameter(Teacher.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Teacher.TEACHER_NAME);

        teacherService.save(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.TEACHERL_UPDATE)
    public void update() {
        Teacher model = getParameter(Teacher.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Teacher.TEACHER_ID, Teacher.TEACHER_NAME);

        teacherService.update(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.TEACHER_DELETE)
    public void delete() {
        Teacher model = getParameter(Teacher.class);
        String request_user_id = getRequest_user_id();

        model.validate(Teacher.TEACHER_ID);

        teacherService.delete(model, request_user_id);

        renderSuccessJson();
    }

}