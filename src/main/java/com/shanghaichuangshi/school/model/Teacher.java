package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Teacher extends Model<Teacher> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "老师编号")
    public static final String TEACHER_ID = "teacher_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "班级编号")
    public static final String CLAZZ_ID = "clazz_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "老师姓名")
    public static final String TEACHER_NAME = "teacher_name";

    
    public String getTeacher_id() {
        return getStr(TEACHER_ID);
    }

    public void setTeacher_id(String teacher_id) {
        set(TEACHER_ID, teacher_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getClazz_id() {
        return getStr(CLAZZ_ID);
    }

    public void setClazz_id(String clazz_id) {
        set(CLAZZ_ID, clazz_id);
    }

    public String getTeacher_name() {
        return getStr(TEACHER_NAME);
    }

    public void setTeacher_name(String teacher_name) {
        set(TEACHER_NAME, teacher_name);
    }
}