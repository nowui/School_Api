package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.school.cache.ConfigCache;
import com.shanghaichuangshi.school.model.Config;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ConfigDao extends Dao {

    private static final ConfigCache configCache = new ConfigCache();

//    public int count() {
//        JMap map = JMap.create();
//        SqlPara sqlPara = Db.getSqlPara("config.count", map);
//
//        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
//        return count.intValue();
//    }
//
//    public List<Config> list(Integer m, Integer n) {
//        JMap map = JMap.create();
//        map.put(Authorization.M, m);
//        map.put(Authorization.N, n);
//        SqlPara sqlPara = Db.getSqlPara("config.list", map);
//
//        return new Config().find(sqlPara.getSql(), sqlPara.getPara());
//    }

    public Config find() {
        Config config = configCache.getConfig();

        if (config == null) {
            JMap map = JMap.create();
            SqlPara sqlPara = Db.getSqlPara("config.find", map);

            List<Config> configList = new Config().find(sqlPara.getSql(), sqlPara.getPara());
            if (configList.size() == 0) {
                config = null;
            } else {
                config = configList.get(0);
            }

            configCache.setConfig(config);
        }

        return config;
    }

    public Config save(Config config, String request_user_id) {
        config.setConfig_id(Util.getRandomUUID());
        config.setSystem_create_user_id(request_user_id);
        config.setSystem_create_time(new Date());
        config.setSystem_update_user_id(request_user_id);
        config.setSystem_update_time(new Date());
        config.setSystem_status(true);

        config.save();

        return config;
    }

    public boolean update(Config config, String request_user_id) {
        configCache.removeConfig();

        JMap map = JMap.create();
        map.put(Config.CONFIG_APPLY_START_TIME, config.getConfig_apply_start_time());
        map.put(Config.CONFIG_APPLY_END_TIME, config.getConfig_apply_end_time());
        map.put(Config.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Config.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("config.update", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}