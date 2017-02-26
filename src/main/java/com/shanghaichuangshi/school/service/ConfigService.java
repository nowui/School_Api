package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.school.dao.ConfigDao;
import com.shanghaichuangshi.school.model.Config;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.DateUtil;

public class ConfigService extends Service {

    private static final ConfigDao configDao = new ConfigDao();

//    public int count(Config config) {
//        return configDao.count();
//    }
//
//    public List<Config> list(Config config, int m, int n) {
//        return configDao.list(m, n);
//    }

    public Config find() {
        return configDao.find();
    }

    public Config save(Config config, String request_user_id) {
        DateUtil.getDateTime(config.getConfig_apply_start_time());
        DateUtil.getDateTime(config.getConfig_apply_end_time());

        return configDao.save(config, request_user_id);
    }

    public boolean update(Config config, String request_user_id) {
        DateUtil.getDateTime(config.getConfig_apply_start_time());
        DateUtil.getDateTime(config.getConfig_apply_end_time());

        return configDao.update(config, request_user_id);
    }

//    public boolean delete(Config config, String request_user_id) {
//        return configDao.delete(config.getConfig_id(), request_user_id);
//    }

}