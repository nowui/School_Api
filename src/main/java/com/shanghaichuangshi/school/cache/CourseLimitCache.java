package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.school.model.Course;

import java.util.List;

public class CourseLimitCache extends Cache {

    private final String model_key = "course_limit_cache_model";

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
