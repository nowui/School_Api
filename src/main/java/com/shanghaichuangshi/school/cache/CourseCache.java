package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.school.model.Course;

import java.util.List;

public class CourseCache extends Cache {

    private final String list_key = "course_cache_list";
    private final String model_key = "course_cache_model";

    public List<Course> getCourseListByUser_id(String user_id) {
        return ehcacheList.get(list_key + "_" + user_id);
    }

    public void setCourseListByUser_id(List<Course> courseList, String user_id) {
        ehcacheList.put(list_key + "_" + user_id, courseList);

        setMapByKeyAndId(list_key, user_id);
    }

    public void removeCourseList() {
        ehcacheList.removeAll(getMapByKey(list_key));

        removeMapByKey(list_key);
    }

    public void removeCourseListByUser_id(String user_id) {
        ehcacheList.remove(list_key + "_" + user_id);

        removeMapByKeyAndId(list_key, user_id);
    }

    public Course getCourseByCourse_id(String course_id) {
        return (Course) ehcacheObject.get(model_key + "_" + course_id);
    }

    public void setCourseByCourse_id(Course course, String course_id) {
        ehcacheObject.put(model_key + "_" + course_id, course);

        setMapByKeyAndId(model_key, course_id);
    }

    public void removeCourse() {
        ehcacheObject.removeAll(getMapByKey(model_key));

        removeMapByKey(model_key);
    }

    public void removeCourseByCourse_id(String course_id) {
        ehcacheObject.remove(model_key + "_" + course_id);

        removeMapByKeyAndId(model_key, course_id);
    }

}
