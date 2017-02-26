package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class CourseApplyHistory extends Model<CourseApplyHistory> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String COURSE_APPLY_HISTORY_ID = "course_apply_history_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String COURSE_ID = "course_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "")
    public static final String COURSE_APPLY_HISTORY_STATUS = "course_apply_history_status";

    
    public String getCourse_apply_history_id() {
        return getStr(COURSE_APPLY_HISTORY_ID);
    }

    public void setCourse_apply_history_id(String course_apply_history_id) {
        set(COURSE_APPLY_HISTORY_ID, course_apply_history_id);
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

    public boolean getCourse_apply_history_status() {
        return getBoolean(COURSE_APPLY_HISTORY_STATUS);
    }

    public void setCourse_apply_history_status(boolean course_apply_history_status) {
        set(COURSE_APPLY_HISTORY_STATUS, course_apply_history_status);
    }
}