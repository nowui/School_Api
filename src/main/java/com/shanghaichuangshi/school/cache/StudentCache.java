package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.school.model.Student;

public class StudentCache extends Cache {

    private final String model_key = "student_cache_model";

    public Student getStudentByUser_id(String user_id) {
        return (Student) ehcacheObject.get(model_key + "_" + user_id);
    }

    public void setStudentByUser_id(Student student, String user_id) {
        ehcacheObject.put(model_key + "_" + user_id, student);

        setMapByKeyAndId(model_key, user_id);
    }

    public void removeStudent() {
        ehcacheObject.removeAll(getMapByKey(model_key));

        removeMapByKey(model_key);
    }

    public void removeStudentByUser_id(String user_id) {
        ehcacheObject.remove(model_key + "_" + user_id);

        removeMapByKeyAndId(model_key, user_id);
    }

}
