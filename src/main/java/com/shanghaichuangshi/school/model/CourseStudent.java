package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class CourseStudent extends Model<CourseStudent> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "课程学生编号")
    public static final String COURSE_STUDENT_ID = "course_student_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "课程编号")
    public static final String COURSE_ID = "course_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "学生编号")
    public static final String STUDENT_ID = "student_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "课程学生类型")
    public static final String COURSE_STUDENT_TYPE = "course_student_type";

    
    public String getCourse_student_id() {
        return getStr(COURSE_STUDENT_ID);
    }

    public void setCourse_student_id(String course_student_id) {
        set(COURSE_STUDENT_ID, course_student_id);
    }

    public String getCourse_id() {
        return getStr(COURSE_ID);
    }

    public void setCourse_id(String course_id) {
        set(COURSE_ID, course_id);
    }

    public String getStudent_id() {
        return getStr(STUDENT_ID);
    }

    public void setStudent_id(String student_id) {
        set(STUDENT_ID, student_id);
    }

    public String getCourse_student_type() {
        return getStr(COURSE_STUDENT_TYPE);
    }

    public void setCourse_student_type(String course_student_type) {
        set(COURSE_STUDENT_TYPE, course_student_type);
    }

}