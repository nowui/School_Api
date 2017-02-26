package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.school.model.CourseStudent;

import java.util.List;

public class CourseStudentCache extends Cache {

    private final String list_key = "course_student_black_cache_list";

    public List<CourseStudent> getCourseStudentBlackListByCourse_id(String course_id) {
        return ehcacheList.get(list_key + "_" + course_id);
    }

    public void setCourseStudentBlackListByCourse_id(List<CourseStudent> courseStudentList, String course_id) {
        ehcacheList.put(list_key + "_" + course_id, courseStudentList);

        setMapByKeyAndId(list_key, course_id);
    }

    public void removeCourseStudentBlackList() {
        ehcacheList.removeAll(getMapByKey(list_key));

        removeMapByKey(list_key);
    }

    public void removeCourseStudentBlackListByCourse_id(String course_id) {
        ehcacheList.remove(list_key + "_" + course_id);

        removeMapByKeyAndId(list_key, course_id);
    }

}
