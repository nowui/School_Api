package com.shanghaichuangshi.school.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.school.cache.ClazzCache;
import com.shanghaichuangshi.school.model.Clazz;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ClazzDao extends Dao {

    private static final ClazzCache clazzCache = new ClazzCache();

    public int count(String clazz_name) {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("clazz.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Clazz> list(String clazz_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Clazz.CLAZZ_NAME, clazz_name);
        map.put(Authorization.M, m);
        map.put(Authorization.N, n);
        SqlPara sqlPara = Db.getSqlPara("clazz.list", map);

        return new Clazz().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Clazz find(String clazz_id) {
        Clazz clazz = clazzCache.getClazzByClazz_id(clazz_id);

        if (clazz == null) {
            JMap map = JMap.create();
            map.put(Clazz.CLAZZ_ID, clazz_id);
            SqlPara sqlPara = Db.getSqlPara("clazz.find", map);

            List<Clazz> clazzList = new Clazz().find(sqlPara.getSql(), sqlPara.getPara());
            if (clazzList.size() == 0) {
                clazz = null;
            } else {
                clazz = clazzList.get(0);
            }

            clazzCache.setClazzByClazz_id(clazz, clazz_id);
        }

        return clazz;
    }

    public Clazz save(Clazz clazz, String request_user_id) {
        clazz.setClazz_id(Util.getRandomUUID());
        clazz.setSystem_create_user_id(request_user_id);
        clazz.setSystem_create_time(new Date());
        clazz.setSystem_update_user_id(request_user_id);
        clazz.setSystem_update_time(new Date());
        clazz.setSystem_status(true);

        clazz.save();

        return clazz;
    }

    public boolean update(Clazz clazz, String request_user_id) {
        clazzCache.removeClazzByClazz_id(clazz.getClazz_id());

        clazz.remove(Clazz.SYSTEM_CREATE_USER_ID);
        clazz.remove(Clazz.SYSTEM_CREATE_TIME);
        clazz.setSystem_update_user_id(request_user_id);
        clazz.setSystem_update_time(new Date());
        clazz.remove(Clazz.SYSTEM_STATUS);

        return clazz.update();
    }

    public boolean delete(String clazz_id, String request_user_id) {
        clazzCache.removeClazzByClazz_id(clazz_id);

        JMap map = JMap.create();
        map.put(Clazz.CLAZZ_ID, clazz_id);
        map.put(Clazz.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Clazz.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("clazz.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}