package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class CourseApply extends Model<CourseApply> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String COURSE_APPLY_ID = "course_apply_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String COURSE_ID = "course_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String USER_ID = "user_id";

    
    public String getCourse_apply_id() {
        return getStr(COURSE_APPLY_ID);
    }

    public void setCourse_apply_id(String course_apply_id) {
        set(COURSE_APPLY_ID, course_apply_id);
    }

    public String getCourse_id() {
        return getStr(COURSE_ID);
    }

    public void setCourse_id(String course_id) {
        set(COURSE_ID, course_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }
}