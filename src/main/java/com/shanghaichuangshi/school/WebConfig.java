package com.shanghaichuangshi.school;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.shanghaichuangshi.controller.*;
import com.shanghaichuangshi.interceptor.GlobalActionInterceptor;
import com.shanghaichuangshi.model.*;
import com.shanghaichuangshi.school.constant.Url;
import com.shanghaichuangshi.school.controller.*;
import com.shanghaichuangshi.school.model.*;

import java.util.ArrayList;
import java.util.List;

public class WebConfig extends JFinalConfig {

    public static void main(String[] args) {
        JFinal.start("WebRoot", 80, "/");
    }

    public void configConstant(Constants constants) {
        constants.setDevMode(false);
        constants.setViewType(ViewType.JSP);
        constants.setError404View("/error.jsp");
    }

    public void configRoute(Routes routes) {
        routes.add("/admin", AdminController.class);
        routes.add("/authorization", AuthorizationController.class);
        routes.add("/log", LogController.class);
        routes.add("/category", CategoryController.class);
        routes.add("/code", CodeController.class);
        routes.add("/attribute", AttributeController.class);
        routes.add("/resource", ResourceController.class);
        routes.add("/role", RoleController.class);
        routes.add("/upload", UploadController.class);
        routes.add("/file", FileController.class);
        routes.add("/clazz", ClazzController.class);
        routes.add("/student", StudentController.class);
        routes.add("/teacher", TeacherController.class);
        routes.add("/course", CourseController.class);
        routes.add("/config", ConfigController.class);
    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        PropKit.use("Jdbc.properties");

        final String URL = PropKit.get("jdbcUrl");
        final String USERNAME = PropKit.get("user");
        final String PASSWORD = PropKit.get("password");
        final Integer INITIALSIZE = PropKit.getInt("initialSize");
        final Integer MIDIDLE = PropKit.getInt("minIdle");
        final Integer MAXACTIVEE = PropKit.getInt("maxActivee");

        DruidPlugin druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
        druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
        druidPlugin.setFilters("stat,wall");
        plugins.add(druidPlugin);

        Slf4jLogFilter sql_log_filter = new Slf4jLogFilter();
        sql_log_filter.setConnectionLogEnabled(false);
        sql_log_filter.setStatementLogEnabled(false);
        sql_log_filter.setStatementExecutableSqlLogEnable(true);
        sql_log_filter.setResultSetLogEnabled(false);
        druidPlugin.addFilter(sql_log_filter);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        activeRecordPlugin.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/WEB-INF/sql/");
        activeRecordPlugin.addSqlTemplate("Code.sql");

        activeRecordPlugin.addMapping("table_admin", "admin_id", Admin.class);
        activeRecordPlugin.addSqlTemplate("Admin.sql");
        activeRecordPlugin.addMapping("table_authorization", "authorization_id", Authorization.class);
        activeRecordPlugin.addSqlTemplate("Authorization.sql");
        activeRecordPlugin.addMapping("table_category", "category_id", Category.class);
        activeRecordPlugin.addSqlTemplate("Category.sql");
        activeRecordPlugin.addMapping("table_log", "log_id", Log.class);
        activeRecordPlugin.addSqlTemplate("Log.sql");
        activeRecordPlugin.addMapping("table_user", "user_id", User.class);
        activeRecordPlugin.addSqlTemplate("User.sql");
        activeRecordPlugin.addMapping("table_file", "file_id", File.class);
        activeRecordPlugin.addSqlTemplate("File.sql");
        activeRecordPlugin.addMapping("table_attribute", "attribute_id", Attribute.class);
        activeRecordPlugin.addSqlTemplate("Attribute.sql");
        activeRecordPlugin.addMapping("table_resource", "resource_id", Resource.class);
        activeRecordPlugin.addSqlTemplate("Resource.sql");
        activeRecordPlugin.addMapping("table_role", "role_id", Role.class);
        activeRecordPlugin.addSqlTemplate("Role.sql");
        activeRecordPlugin.addMapping("table_clazz", "clazz_id", Clazz.class);
        activeRecordPlugin.addSqlTemplate("Clazz.sql");
        activeRecordPlugin.addMapping("table_student", "student_id", Student.class);
        activeRecordPlugin.addSqlTemplate("Student.sql");
        activeRecordPlugin.addMapping("table_teacher", "teacher_id", Teacher.class);
        activeRecordPlugin.addSqlTemplate("Teacher.sql");
        activeRecordPlugin.addMapping("table_course", "course_id", Course.class);
        activeRecordPlugin.addSqlTemplate("Course.sql");
        activeRecordPlugin.addMapping("table_course_student", "course_student_id", CourseStudent.class);
        activeRecordPlugin.addSqlTemplate("CourseStudent.sql");
        activeRecordPlugin.addMapping("table_course_apply", "course_apply_id", CourseApply.class);
        activeRecordPlugin.addSqlTemplate("CourseApply.sql");
        activeRecordPlugin.addMapping("table_course_apply_history", "course_apply_history_id", CourseApplyHistory.class);
        activeRecordPlugin.addSqlTemplate("CourseApplyHistory.sql");
        activeRecordPlugin.addMapping("table_config", "config_id", Config.class);
        activeRecordPlugin.addSqlTemplate("Config.sql");

        plugins.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        List<String> uncheckTokenUrlList = new ArrayList<String>();
        uncheckTokenUrlList.add(Url.STUDENT_LOGIN);
        uncheckTokenUrlList.add(Url.COURSE_APPLY_EXPORT);

        List<String> uncheckParameterUrlList = new ArrayList<String>();
        uncheckParameterUrlList.add(Url.STUDENT_UPLOAD);
        uncheckParameterUrlList.add(Url.COURSE_UPLOAD);

        List<String> uncheckHeaderUrlList = new ArrayList<String>();
        uncheckHeaderUrlList.add(Url.COURSE_EXPORT);
        uncheckHeaderUrlList.add(Url.COURSE_APPLY_EXPORT);

        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor(uncheckTokenUrlList, uncheckParameterUrlList, uncheckHeaderUrlList));
    }

    public void configHandler(Handlers handlers) {

    }

}