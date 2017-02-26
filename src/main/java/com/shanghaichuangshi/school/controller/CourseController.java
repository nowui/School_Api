package com.shanghaichuangshi.school.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.school.model.Course;
import com.shanghaichuangshi.school.model.CourseApply;
import com.shanghaichuangshi.school.model.CourseStudent;
import com.shanghaichuangshi.school.service.CourseService;
import com.shanghaichuangshi.school.service.CourseStudentService;
import com.shanghaichuangshi.school.type.CourseStudentType;

import java.util.List;

public class CourseController extends Controller {

    private static final CourseService courseService = new CourseService();
    private static final CourseStudentService courseWhileService = new CourseStudentService();

    @ActionKey(Url.COURSE_LIST)
    public void list() {
        String request_user_id = getRequest_user_id();

        List<Course> courseList = courseService.listByUser_id(request_user_id);

        renderSuccessJson(courseList);
    }

    @ActionKey(Url.COURSE_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Course model = getParameter(Course.class);

        model.validate(Course.COURSE_NAME);

        int count = courseService.count(model);

        List<Course> courseList = courseService.list(model, getM(), getN());

        renderSuccessJson(count, courseList);
    }

    @ActionKey(Url.COURSE_FIND)
    public void find() {
        Course model = getParameter(Course.class);
        String request_user_id = getRequest_user_id();

        model.validate(Course.COURSE_ID);

        Course course = courseService.find(model.getCourse_id(), request_user_id);

        course.removeUnfindable();

        renderSuccessJson(course);
    }

    @ActionKey(Url.COURSE_ADMIN_FIND)
    public void adminFind() {
        Course model = getParameter(Course.class);
        String request_user_id = getRequest_user_id();

        model.validate(Course.COURSE_ID);

        Course course = courseService.find(model.getCourse_id(), request_user_id);

        renderSuccessJson(course);
    }

    @ActionKey(Url.COURSE_SAVE)
    public void save() {
        Course model = getParameter(Course.class);
        String request_user_id = getRequest_user_id();

        model.validate(Course.COURSE_NAME);

        courseService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSEL_UPDATE)
    public void update() {
        Course model = getParameter(Course.class);
        String request_user_id = getRequest_user_id();

        model.validate(Course.COURSE_ID, Course.COURSE_NAME);

        courseService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_DELETE)
    public void delete() {
        Course model = getParameter(Course.class);
        String request_user_id = getRequest_user_id();

        model.validate(Course.COURSE_ID);

        courseService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_ALL_DELETE)
    public void deleteAll() {
        String request_user_id = getRequest_user_id();

        courseService.deleteAll(request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_EXPORT)
    public void export() {
        render(courseService.export());
    }

    @ActionKey(Url.COURSE_UPLOAD)
    public void upload() {
        String request_user_id = getRequest_user_id();

        UploadFile uploadFile = getFile("file", request_user_id, 1024 * 1024 * 2);

        courseService.upload(uploadFile, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_STUDENT_WHITE_LIST)
    public void studentWhiteList() {
        CourseStudent model = getParameter(CourseStudent.class);

        model.validate(CourseStudent.COURSE_ID);

        model.setCourse_student_type(CourseStudentType.WHITE.getKey());

        List<CourseStudent> courseWhileList = courseWhileService.list(model.getCourse_id(), CourseStudentType.WHITE.getKey());

        renderSuccessJson(courseWhileList);
    }

    @ActionKey(Url.COURSE_STUDENT_WHITE_APPLY_SAVE)
    public void studentWhiteApplySave() {
        String request_user_id = getRequest_user_id();

        courseService.studentWhiteApplySave(request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_STUDENT_WHITE_SAVE)
    public void studentWhiteSave() {
        CourseStudent model = getParameter(CourseStudent.class);
        String request_user_id = getRequest_user_id();

        model.validate(CourseStudent.COURSE_ID, CourseStudent.STUDENT_ID);

        model.setCourse_student_type(CourseStudentType.WHITE.getKey());

        courseWhileService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_STUDENT_BLACK_LIST)
    public void studenBlackList() {
        CourseStudent model = getParameter(CourseStudent.class);

        model.validate(CourseStudent.COURSE_ID);

        model.setCourse_student_type(CourseStudentType.BLACK.getKey());

        List<CourseStudent> courseWhileList = courseWhileService.list(model.getCourse_id(), CourseStudentType.BLACK.getKey());

        renderSuccessJson(courseWhileList);
    }

    @ActionKey(Url.COURSE_STUDENT_BLACK_SAVE)
    public void studenBlackSave() {
        CourseStudent model = getParameter(CourseStudent.class);
        String request_user_id = getRequest_user_id();

        model.validate(CourseStudent.COURSE_ID, CourseStudent.STUDENT_ID);

        model.setCourse_student_type(CourseStudentType.BLACK.getKey());

        courseWhileService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_STUDENT_DELETE)
    public void studentDelete() {
        CourseStudent model = getParameter(CourseStudent.class);
        String request_user_id = getRequest_user_id();

        model.validate(CourseStudent.COURSE_STUDENT_ID);

        courseWhileService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_APPLY_LIST)
    public void applyList() {
        String request_user_id = getRequest_user_id();

        List<CourseApply> courseApplyList = courseService.applyList(request_user_id);

        renderSuccessJson(courseApplyList);
    }

    @ActionKey(Url.COURSE_APPLY_SAVE)
    public void applySave() {
        CourseApply model = getParameter(CourseApply.class);
        String request_user_id = getRequest_user_id();

        model.validate(CourseApply.COURSE_ID);

        courseService.applySave(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_APPLY_DELETE)
    public void applyDelete() {
        CourseApply model = getParameter(CourseApply.class);
        String request_user_id = getRequest_user_id();

        model.validate(CourseApply.COURSE_ID);

        courseService.applyDelete(model.getCourse_id(), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_APPLY_ALL_DELETE)
    public void applyAllDelete() {
        String request_user_id = getRequest_user_id();

        courseService.applyAllDelete(request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.COURSE_APPLY_EXPORT)
    public void applyExport() {
        render(courseService.applyExport());
    }

}