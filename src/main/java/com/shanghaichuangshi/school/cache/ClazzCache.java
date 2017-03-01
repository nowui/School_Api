package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.school.model.Clazz;

public class ClazzCache extends Cache {

    private final String model_key = "clazz_cache_model";

    public Clazz getClazzByClazz_id(String clazz_id) {
        return (Clazz) ehcacheObject.get(model_key + "_" + clazz_id);
    }

    public void setClazzByClazz_id(Clazz clazz, String clazz_id) {
        ehcacheObject.put(model_key + "_" + clazz_id, clazz);

        setMapByKeyAndId(model_key, clazz_id);
    }

    public void removeClazz() {
        ehcacheObject.removeAll(getMapByKey(model_key));

        removeMapByKey(model_key);
    }

    public void removeClazzByClazz_id(String clazz_id) {
        ehcacheObject.remove(model_key + "_" + clazz_id);

        removeMapByKeyAndId(model_key, clazz_id);
    }

}
