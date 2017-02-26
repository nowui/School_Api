package com.shanghaichuangshi.school.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.school.model.Config;
import com.shanghaichuangshi.school.service.ConfigService;

import java.util.List;

public class ConfigController extends Controller {

    private static final ConfigService configService = new ConfigService();

//    @ActionKey(Url.CONFIG_LIST)
//    public void list() {
//        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);
//
//        Config model = getParameter(Config.class);
//
//        List<Config> configList = configService.list(model, getM(), getN());
//
//        renderSuccessJson(configList);
//    }
//
//    @ActionKey(Url.CONFIG_ADMIN_LIST)
//    public void adminList() {
//        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);
//
//        Config model = getParameter(Config.class);
//
//        int count = configService.count(model);
//
//        List<Config> configList = configService.list(model, getM(), getN());
//
//        renderSuccessJson(count, configList);
//    }

    @ActionKey(Url.CONFIG_FIND)
    public void find() {
        Config config = configService.find();

        config.removeUnfindable();

        renderSuccessJson(config);
    }

    @ActionKey(Url.CONFIG_ADMIN_FIND)
    public void adminFind() {
        Config config = configService.find();

        renderSuccessJson(config);
    }

    @ActionKey(Url.CONFIG_SAVE)
    public void save() {
        Config model = getParameter(Config.class);
        String request_user_id = getRequest_user_id();

        model.validate(Config.CONFIG_APPLY_START_TIME, Config.CONFIG_APPLY_END_TIME);

        configService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.CONFIGL_UPDATE)
    public void update() {
        Config model = getParameter(Config.class);
        String request_user_id = getRequest_user_id();

        model.validate(Config.CONFIG_APPLY_START_TIME, Config.CONFIG_APPLY_END_TIME);

        configService.update(model, request_user_id);

        renderSuccessJson();
    }

//    @ActionKey(Url.CONFIG_DELETE)
//    public void delete() {
//        Config model = getParameter(Config.class);
//        String request_user_id = getRequest_user_id();
//
//        model.validate(Config.CONFIG_ID);
//
//        configService.delete(model, request_user_id);
//
//        renderSuccessJson();
//    }

}