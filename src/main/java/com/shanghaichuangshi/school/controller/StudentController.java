package com.shanghaichuangshi.school.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.school.model.Student;
import com.shanghaichuangshi.school.service.StudentService;
import com.shanghaichuangshi.service.UserService;

import java.util.List;
import java.util.Map;

public class StudentController extends Controller {

    private static final StudentService studentService = new StudentService();
    private static final UserService userService = new UserService();

    @ActionKey(Url.STUDENT_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Student model = getParameter(Student.class);

        model.validate(Student.STUDENT_NAME);

        List<Student> studentList = studentService.list(model, getM(), getN());

        renderSuccessJson(studentList);
    }

    @ActionKey(Url.STUDENT_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Student model = getParameter(Student.class);

        model.validate(Student.STUDENT_NAME, Student.CLAZZ_ID);

        int count = studentService.count(model);

        List<Student> studentList = studentService.list(model, getM(), getN());

        renderSuccessJson(count, studentList);
    }

    @ActionKey(Url.STUDENT_FIND)
    public void find() {
        Student model = getParameter(Student.class);

        model.validate(Student.STUDENT_ID);

        Student student = studentService.find(model.getStudent_id());

        student.removeUnfindable();

        renderSuccessJson(student);
    }

    @ActionKey(Url.STUDENT_ADMIN_FIND)
    public void adminFind() {
        Student model = getParameter(Student.class);

        model.validate(Student.STUDENT_ID);

        Student student = studentService.find(model.getStudent_id());

        renderSuccessJson(student);
    }

    @ActionKey(Url.STUDENT_SAVE)
    public void save() {
        Student model = getParameter(Student.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Student.STUDENT_NAME);

        studentService.save(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.STUDENTL_UPDATE)
    public void update() {
        Student model = getParameter(Student.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Student.STUDENT_ID, Student.STUDENT_NAME);

        studentService.update(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.STUDENTL_PASSWORD_UPDATE)
    public void passwordUpdate() {
        User model = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(User.USER_PASSWORD);

        userService.updateByUser_password(model.getUser_password(), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.STUDENT_DELETE)
    public void delete() {
        Student model = getParameter(Student.class);
        String request_user_id = getRequest_user_id();

        model.validate(Student.STUDENT_ID);

        studentService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.STUDENT_ALL_DELETE)
    public void deleteAll() {
        String request_user_id = getRequest_user_id();

        studentService.deleteAll(request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.STUDENT_LOGIN)
    public void login() {
        User model = getParameter(User.class);
        String platform = getPlatform();
        String version = getVersion();
        String ip_address = getIp_address();
        String request_user_id = getRequest_user_id();

        model.validate(User.USER_ACCOUNT, User.USER_PASSWORD);

        Map<String, Object> resultMap = studentService.login(model, platform, version, ip_address, request_user_id);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.STUDENT_UPLOAD)
    public void upload() {
        String request_user_id = getRequest_user_id();

        UploadFile uploadFile = getFile("file", request_user_id, 1024 * 1024 * 2);

        studentService.upload(uploadFile, request_user_id);

        renderSuccessJson();
    }

}