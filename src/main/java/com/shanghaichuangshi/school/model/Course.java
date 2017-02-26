package com.shanghaichuangshi.school.model;

import com.alibaba.fastjson.JSONArray;
import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;
import com.shanghaichuangshi.util.Util;

public class Course extends Model<Course> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "课程编号")
    public static final String COURSE_ID = "course_id";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "班级编号")
    public static final String CLAZZ_ID = "clazz_id";

    @Column(type = ColumnType.VARCHAR, length = 200, comment = "课程老师")
    public static final String COURSE_TEACHER = "course_teacher";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "课程名称")
    public static final String COURSE_NAME = "course_name";

    @Column(type = ColumnType.INT, length = 2, comment = "课程时间")
    public static final String COURSE_TIME = "course_time";

    @Column(type = ColumnType.INT, length = 3, comment = "申请限制")
    public static final String COURSE_APPLY_LIMIT = "course_apply_limit";

    @Column(type = ColumnType.VARCHAR, length = 200, comment = "课程地址")
    public static final String COURSE_ADDRESS = "course_address";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "课程图片")
    public static final String COURSE_IMAGE = "course_image";

    @Column(type = ColumnType.LONGTEXT, length = 0, comment = "课程介绍")
    public static final String COURSE_CONTENT = "course_content";

    
    public String getCourse_id() {
        return getStr(COURSE_ID);
    }

    public void setCourse_id(String course_id) {
        set(COURSE_ID, course_id);
    }

    public String getClazz_id() {
        return getStr(CLAZZ_ID);
    }

    public void setClazz_id(String clazz_id) {
        set(CLAZZ_ID, clazz_id);
    }

    public String getCourse_teacher() {
        return getStr(COURSE_TEACHER);
    }

    public void setCourse_teacher(String course_teacher) {
        set(COURSE_TEACHER, course_teacher);
    }

    public String getCourse_name() {
        return getStr(COURSE_NAME);
    }

    public void setCourse_name(String course_name) {
        set(COURSE_NAME, course_name);
    }

    public Integer getCourse_time() {
        return getInt(COURSE_TIME);
    }

    public void setCourse_time(Integer course_time) {
        set(COURSE_TIME, course_time);
    }

    public Integer getCourse_apply_limit() {
        return getInt(COURSE_APPLY_LIMIT);
    }

    public void setCourse_apply_limit(Integer course_apply_limit) {
        set(COURSE_APPLY_LIMIT, course_apply_limit);
    }

    public String getCourse_address() {
        return getStr(COURSE_ADDRESS);
    }

    public void setCourse_address(String course_address) {
        set(COURSE_ADDRESS, course_address);
    }

    public String getCourse_image() {
        return getStr(COURSE_IMAGE);
    }

    public void setCourse_image(String course_image) {
        set(COURSE_IMAGE, course_image);
    }

    public String getCourse_content() {
        return getStr(COURSE_CONTENT);
    }

    public void setCourse_content(String course_content) {
        set(COURSE_CONTENT, course_content);
    }
}