package com.shanghaichuangshi.school.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.school.model.Clazz;
import com.shanghaichuangshi.school.service.ClazzService;

import java.util.List;

public class ClazzController extends Controller {

    private static final ClazzService clazzService = new ClazzService();

    @ActionKey(Url.CLAZZ_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Clazz model = getParameter(Clazz.class);

        model.validate(Clazz.CLAZZ_NAME);

        List<Clazz> clazzList = clazzService.list(model, getM(), getN());

        renderSuccessJson(clazzList);
    }

    @ActionKey(Url.CLAZZ_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Clazz model = getParameter(Clazz.class);

        model.validate(Clazz.CLAZZ_NAME);

        int count = clazzService.count(model);

        List<Clazz> clazzList = clazzService.list(model, getM(), getN());

        renderSuccessJson(count, clazzList);
    }

    @ActionKey(Url.CLAZZ_FIND)
    public void find() {
        Clazz model = getParameter(Clazz.class);

        model.validate(Clazz.CLAZZ_ID);

        Clazz clazz = clazzService.find(model.getClazz_id());

        clazz.removeUnfindable();

        renderSuccessJson(clazz);
    }

    @ActionKey(Url.CLAZZ_ADMIN_FIND)
    public void adminFind() {
        Clazz model = getParameter(Clazz.class);

        model.validate(Clazz.CLAZZ_ID);

        Clazz clazz = clazzService.find(model.getClazz_id());

        renderSuccessJson(clazz);
    }

    @ActionKey(Url.CLAZZ_SAVE)
    public void save() {
        Clazz model = getParameter(Clazz.class);
        String request_user_id = getRequest_user_id();

        model.validate(Clazz.CLAZZ_NAME);

        clazzService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.CLAZZL_UPDATE)
    public void update() {
        Clazz model = getParameter(Clazz.class);
        String request_user_id = getRequest_user_id();

        model.validate(Clazz.CLAZZ_ID, Clazz.CLAZZ_NAME);

        clazzService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.CLAZZ_DELETE)
    public void delete() {
        Clazz model = getParameter(Clazz.class);
        String request_user_id = getRequest_user_id();

        model.validate(Clazz.CLAZZ_ID);

        clazzService.delete(model, request_user_id);

        renderSuccessJson();
    }

}