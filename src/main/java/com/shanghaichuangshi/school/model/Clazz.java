package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Clazz extends Model<Clazz> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String CLAZZ_ID = "clazz_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "班级名称")
    public static final String CLAZZ_NAME = "clazz_name";

    @Column(type = ColumnType.INT, length = 3, comment = "班级选课限制")
    public static final String CLAZZ_COURSE_APPLY_LIMIT = "clazz_course_apply_limit";

    @Column(type = ColumnType.VARCHAR, length = 19, comment = "班级选课开始时间")
    public static final String CLAZZ_COURSE_APPLY_START_TIME = "clazz_course_apply_start_time";

    @Column(type = ColumnType.VARCHAR, length = 19, comment = "班级选课结束时间")
    public static final String CLAZZ_COURSE_APPLY_END_TIME = "clazz_course_apply_end_time";

    @Column(type = ColumnType.INT, length = 3, comment = "班级排序")
    public static final String CLAZZ_SORT = "clazz_sort";

    
    public String getClazz_id() {
        return getStr(CLAZZ_ID);
    }

    public void setClazz_id(String clazz_id) {
        set(CLAZZ_ID, clazz_id);
    }

    public String getClazz_name() {
        return getStr(CLAZZ_NAME);
    }

    public void setClazz_name(String clazz_name) {
        set(CLAZZ_NAME, clazz_name);
    }

    public Integer getClazz_course_apply_limit() {
        return getInt(CLAZZ_COURSE_APPLY_LIMIT);
    }

    public void setClazz_course_apply_limit(Integer clazz_course_apply_limit) {
        set(CLAZZ_COURSE_APPLY_LIMIT, clazz_course_apply_limit);
    }

    public String getClazz_course_apply_start_time() {
        return getStr(CLAZZ_COURSE_APPLY_START_TIME);
    }

    public void setClazz_course_apply_start_time(String clazz_course_apply_start_time) {
        set(CLAZZ_COURSE_APPLY_START_TIME, clazz_course_apply_start_time);
    }

    public String getClazz_course_apply_end_time() {
        return getStr(CLAZZ_COURSE_APPLY_END_TIME);
    }

    public void setClazz_course_apply_end_time(String clazz_course_apply_end_time) {
        set(CLAZZ_COURSE_APPLY_END_TIME, clazz_course_apply_end_time);
    }

    public Integer getClazz_sort() {
        return getInt(CLAZZ_SORT);
    }

    public void setClazz_sort(Integer clazz_sort) {
        set(CLAZZ_SORT, clazz_sort);
    }
}