package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.school.model.CourseApply;

import java.util.List;

public class CourseApplyCache extends Cache {

    private final String list_key = "course_apply_cache_list";

    public List<CourseApply> getCourseApplyListByUser_id(String user_id) {
        return ehcacheList.get(list_key + "_" + user_id);
    }

    public void setCourseApplyListByUser_id(List<CourseApply> courseList, String user_id) {
        ehcacheList.put(list_key + "_" + user_id, courseList);

        setMapByKeyAndId(list_key, user_id);
    }

    public void removeCourseApplyList() {
        ehcacheList.removeAll(getMapByKey(list_key));

        removeMapByKey(list_key);
    }

    public void removeCourseApplyListByUser_id(String user_id) {
        ehcacheList.remove(list_key + "_" + user_id);

        removeMapByKeyAndId(list_key, user_id);
    }
    
}
