package com.shanghaichuangshi.school.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.school.model.Config;

public class ConfigCache extends Cache {

    private final String model_key = "config_cache_model";

    public Config getConfig() {
        return (Config) ehcacheObject.get(model_key);
    }

    public void setConfig(Config config) {
        ehcacheObject.put(model_key, config);
    }

    public void removeConfig() {
        ehcacheObject.remove(model_key);
    }

}
