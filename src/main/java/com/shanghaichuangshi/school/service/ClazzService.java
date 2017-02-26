package com.shanghaichuangshi.school.service;

import com.shanghaichuangshi.school.dao.ClazzDao;
import com.shanghaichuangshi.school.model.Clazz;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class ClazzService extends Service {

    private static final ClazzDao clazzDao = new ClazzDao();

    public int count(Clazz clazz) {
        return clazzDao.count(clazz.getClazz_name());
    }

    public List<Clazz> list(Clazz clazz, int m, int n) {
        return clazzDao.list(clazz.getClazz_name(), m, n);
    }

    public Clazz find(String clazz_id) {
        return clazzDao.find(clazz_id);
    }

    public Clazz save(Clazz clazz, String request_user_id) {
        return clazzDao.save(clazz, request_user_id);
    }

    public boolean update(Clazz clazz, String request_user_id) {
        return clazzDao.update(clazz, request_user_id);
    }

    public boolean delete(Clazz clazz, String request_user_id) {
        return clazzDao.delete(clazz.getClazz_id(), request_user_id);
    }

}