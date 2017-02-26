package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Student extends Model<Student> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String STUDENT_ID = "student_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String CLAZZ_ID = "clazz_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String STUDENT_NAME = "student_name";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "")
    public static final String STUDENT_NUMBER = "student_number";

    @Column(type = ColumnType.VARCHAR, length = 2, comment = "")
    public static final String STUDENT_SEX = "student_sex";

    
    public String getStudent_id() {
        return getStr(STUDENT_ID);
    }

    public void setStudent_id(String student_id) {
        set(STUDENT_ID, student_id);
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

    public String getStudent_name() {
        return getStr(STUDENT_NAME);
    }

    public void setStudent_name(String student_name) {
        set(STUDENT_NAME, student_name);
    }

    public String getStudent_number() {
        return getStr(STUDENT_NUMBER);
    }

    public void setStudent_number(String student_number) {
        set(STUDENT_NUMBER, student_number);
    }

    public String getStudent_sex() {
        return getStr(STUDENT_SEX);
    }

    public void setStudent_sex(String student_sex) {
        set(STUDENT_SEX, student_sex);
    }
}