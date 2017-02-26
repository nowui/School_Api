package com.shanghaichuangshi.school.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.shanghaichuangshi.render.ExcelRender;
import com.shanghaichuangshi.school.cache.CourseLimitCache;
import com.shanghaichuangshi.school.dao.CourseDao;
import com.shanghaichuangshi.school.model.*;
import com.shanghaichuangshi.school.type.CourseStudentType;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class CourseService extends Service {

    private static final CourseDao courseDao = new CourseDao();

    private static final ClazzService clazzService = new ClazzService();
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final CourseStudentService courseStudentService = new CourseStudentService();
    private static final CourseApplyService courseApplyService = new CourseApplyService();
    private static final ConfigService configService = new ConfigService();
    private static final TeacherService teacherService = new TeacherService();

    private static final CourseLimitCache courseLimitCache = new CourseLimitCache();

    public int count(Course course) {
        return courseDao.count(course.getCourse_name());
    }

    public List<Course> list(Course course, int m, int n) {
        return courseDao.list(course.getCourse_name(), m, n);
    }

    public List<Course> listByUser_id(String user_id) {
        List<Course> courseList = courseDao.listByUser_id(user_id);

        List<CourseApply> courseApplyList = courseApplyService.list(user_id);

        for (Course course : courseList) {
            course.put("is_apply", false);

            for (CourseApply courseApply : courseApplyList) {
                if (course.getCourse_id().equals(courseApply.getCourse_id())) {
                    course.put("is_apply", true);

                    break;
                }
            }
        }

        return courseList;
    }

    public Course find(String course_id, String request_user_id) {
        Course course = courseDao.find(course_id);

        List<CourseApply> courseApplyList = courseApplyService.list(request_user_id);
        boolean isApply = false;
        for (CourseApply courseApply : courseApplyList) {
            if (course.getCourse_id().equals(courseApply.getCourse_id())) {
                isApply = true;

                break;
            }
        }

        course.put("is_apply", isApply);

        if (isApply) {
            course.put("is_limit", false);
        } else {
            Course c = courseLimitCache.getCourseByCourse_id(course.getCourse_id());
            if (c == null) {
                course.put("is_limit", false);

                List<CourseStudent> courseStudentList = courseStudentService.list(course.getCourse_id(), CourseStudentType.BLACK.getKey());
                for (CourseStudent courseStudent : courseStudentList) {
                    Student student = studentService.findByUser_id(request_user_id);

                    if (courseStudent.getStudent_id().equals(student.getStudent_id())) {
                        course.put("is_limit", true);

                        break;
                    }
                }

            } else {
                course.put("is_limit", true);
            }
        }

        return course;
    }

    public Course save(Course course, String request_user_id) {
        return courseDao.save(course, request_user_id);
    }

    public boolean update(Course course, String request_user_id) {
        return courseDao.update(course, request_user_id);
    }

    public boolean delete(Course course, String request_user_id) {
        return courseDao.delete(course.getCourse_id(), request_user_id);
    }

    public boolean deleteAll(String request_user_id) {
        return courseDao.deleteAll(request_user_id);
    }

    public ExcelRender export() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        List<Record> teacherList = Db.find("select teacher_id, teacher_name from teacher where teacher_status = 1");

        List<Record> gradeList = Db.find("select grade_id, grade_name from grade where grade_status = 1");

        HSSFSheet sheet = wb.createSheet("总-按科目");

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("班级");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("老师");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("课程名称");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("课程时间");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("申请限制");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("课程地址");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("课程图片");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("课程介绍");
        cell.setCellStyle(style);

        List<Course> courseList = courseDao.list("", 0, 0);
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);

            String course_clazz = course.getClazz_id();
            for (Record record : gradeList) {
                if (course_clazz.contains(record.getStr("grade_id"))) {
                    course_clazz = course_clazz.replace(record.getStr("grade_id"), record.getStr("grade_name"));
                }
            }
            course_clazz = course_clazz.replace("[\"", "").replace("\"]", "").replace("\",\"", ",");

            String course_teacher = course.getCourse_teacher();
            for (Record record : teacherList) {
                if (course_teacher.contains(record.getStr("teacher_id"))) {
                    course_teacher = course_teacher.replace(record.getStr("teacher_id"), record.getStr("teacher_name"));
                }
            }
            course_teacher = course_teacher.replace("[\"", "").replace("\"]", "").replace("\",\"", ",");

            String courser_time = "";

            switch (course.getCourse_time()) {
                case 17:
                    courser_time = "星期一第七节";
                    break;
                case 27:
                    courser_time = "星期二第七节";
                    break;
                case 28:
                    courser_time = "星期二第八节";
                    break;
                case 47:
                    courser_time = "星期四第七节";
                    break;
                case 48:
                    courser_time = "星期四第八节";
                    break;
                case 56:
                    courser_time = "星期五第六节";
                    break;
            }

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(course_clazz);
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(course_teacher);
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(course.getCourse_name());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(courser_time);
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(course.getCourse_apply_limit());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(course.getCourse_address());
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(course.getCourse_image());
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(course.getCourse_content());
            cell.setCellStyle(style);
        }

        return new ExcelRender(wb, "选课信息");
    }

    public void upload(UploadFile uploadFile, String request_user_id) {
        String suffix = uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf(".") + 1);

        if (".xls.xlsx".contains(suffix)) {
            try {
                InputStream is = new FileInputStream(uploadFile.getFile());
                POIFSFileSystem fs = new POIFSFileSystem(is);
                @SuppressWarnings("resource")
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = wb.getSheetAt(0);
                int trLength = sheet.getLastRowNum();

                List<Clazz> clazzList = clazzService.list(new Clazz(), 0, 0);

                for (int i = 1; i <= trLength; i++) {
                    HSSFRow row = sheet.getRow(i);

                    HSSFCell clazzCell = row.getCell(0);
                    clazzCell.setCellType(CellType.STRING);

                    HSSFCell teacherCell = row.getCell(1);
                    teacherCell.setCellType(CellType.STRING);

                    HSSFCell nameCell = row.getCell(2);
                    nameCell.setCellType(CellType.STRING);

                    HSSFCell timeCell = row.getCell(3);
                    timeCell.setCellType(CellType.STRING);

                    HSSFCell limitCell = row.getCell(4);
                    limitCell.setCellType(CellType.STRING);

                    HSSFCell addressCell = row.getCell(5);
                    addressCell.setCellType(CellType.STRING);

                    HSSFCell contentCell = row.getCell(6);
                    contentCell.setCellType(CellType.STRING);

                    String clazz_id = clazzCell.getStringCellValue();
                    String course_teacher = teacherCell.getStringCellValue();
                    String course_name = nameCell.getStringCellValue();
                    String time = timeCell.getStringCellValue();
                    String course_apply_limit = limitCell.getStringCellValue();
                    String course_address = addressCell.getStringCellValue();
                    String course_content = contentCell.getStringCellValue();

                    Integer course_time = 0;
                    switch (time) {
                        case "星期一第七节":
                            course_time = 17;
                            break;
                        case "星期二第七节":
                            course_time = 27;
                            break;
                        case "星期二第八节":
                            course_time = 28;
                            break;
                        case "星期四第七节":
                            course_time = 47;
                            break;
                        case "星期四第八节":
                            course_time = 48;
                            break;
                        case "星期五第六节":
                            course_time = 56;
                            break;
                    }

                    for (Clazz clazz : clazzList) {
                        if (clazz_id.contains(clazz.getClazz_name())) {
                            clazz_id = clazz_id.replace(clazz.getClazz_name(), clazz.getClazz_id());
                        }
                    }

                    clazz_id = clazz_id.replace(",", "\",\"");
                    clazz_id = "[\"" + clazz_id + "\"]";

                    if (clazz_id != "") {
                        Course course = new Course();
                        course.setClazz_id(clazz_id);
                        course.setCourse_teacher(course_teacher);
                        course.setCourse_name(course_name);
                        course.setCourse_time(course_time);
                        course.setCourse_apply_limit(Integer.valueOf(course_apply_limit));
                        course.setCourse_address(course_address);
                        course.setCourse_image("[]");
                        course.setCourse_content(course_content);
                        save(course, request_user_id);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("上传文件格式不正确!");
            } catch (IOException e) {
                throw new RuntimeException("上传文件格式不正确!");
            } finally {
                uploadFile.getFile().delete();
            }
        } else {
            uploadFile.getFile().delete();

            throw new RuntimeException("上传文件格式不正确!");
        }
    }

    public List<CourseApply> applyList(String user_id) {
        return courseApplyService.list(user_id);
    }

    private void check(Clazz clazz, String request_user_id) {
        Date nowDate = new Date();


        Date clazzStartDate = DateUtil.getDateTime(clazz.getClazz_course_apply_start_time());
        Date clazzEndDate = DateUtil.getDateTime(clazz.getClazz_course_apply_end_time());

        if(nowDate.before(clazzStartDate)) {
            throw new RuntimeException(clazz.getClazz_name() + "班于" + clazz.getClazz_course_apply_start_time() + "正式开放申请!");
        }

        if(nowDate.after(clazzEndDate)) {
            throw new RuntimeException(clazz.getClazz_name() + "班已经停止申请!");
        }

        Config config = configService.find();

        if (config == null) {
            throw new RuntimeException("该系统还没有开放申请!");
        }

        Date configStartDate = DateUtil.getDateTime(config.getConfig_apply_start_time());
        Date configEndDate = DateUtil.getDateTime(config.getConfig_apply_end_time());

        if(nowDate.before(configStartDate)) {
            throw new RuntimeException(config.getConfig_apply_start_time() + "正式开放申请!");
        }

        if(nowDate.after(configEndDate)) {
            throw new RuntimeException("已经过期，停止申请!");
        }
    }

    public boolean applySave(CourseApply course_apply, String request_user_id) {
        String course_id = course_apply.getCourse_id();

        Student student = studentService.findByUser_id(request_user_id);
        Clazz clazz = clazzService.find(student.getClazz_id());

        check(clazz, request_user_id);

        Course c = courseLimitCache.getCourseByCourse_id(course_id);
        if (c != null) {
            throw new RuntimeException("该课程已经没有名额,不能再申请!");
        }

        Course course = courseDao.find(course_id);

        int courseApplyCount = courseApplyService.countByCourse_id(course_id);

        if (courseApplyCount < course.getCourse_apply_limit()) {
            //是否黑名单
            List<CourseStudent> courseStudentList = courseStudentService.list(course.getCourse_id(), CourseStudentType.BLACK.getKey());
            for (CourseStudent courseStudent : courseStudentList) {
                if (courseStudent.getStudent_id().equals(student.getStudent_id())) {
                    throw new RuntimeException("该课程已经没有名额,不能再申请!");
                }
            }

            int courseTimeCount = courseApplyService.countOneDayByUser_idAndCourse_time(request_user_id, course.getCourse_time());

            if (courseTimeCount == 0) {
                int clazzCourseApplyLimit = courseApplyService.countByUser_id(request_user_id);

                if (clazz.getClazz_course_apply_limit() > clazzCourseApplyLimit) {
                    int count = courseApplyService.countByCourse_idAndUser_id(course_id, request_user_id);

                    if (count == 0) {
                        boolean result = courseApplyService.save(course_id, course, request_user_id);

                        if (result) {

                        } else {
                            throw new RuntimeException("申请不成功，请稍后再试！");
                        }
                    } else {
                        throw new RuntimeException("已经申请过该课程,不能再申请!");
                    }
                } else {
                    throw new RuntimeException("您已经申请了" + clazz.getClazz_course_apply_limit() + "门课程,不能再申请!");
                }
            } else {
                int day = course.getCourse_time() / 10;

                String str = "";
                switch (day) {
                    case 1:
                        str = "星期一";
                        break;
                    case 2:
                        str = "星期二";
                        break;
                    case 3:
                        str = "星期三";
                        break;
                    case 4:
                        str = "星期四";
                        break;
                    case 5:
                        str = "星期五";
                        break;
                    case 6:
                        str = "星期六";
                        break;
                    case 7:
                        str = "星期日";
                        break;
                }

                throw new RuntimeException("您在" + str + "已经申请过课程,不能再申请!");
            }
        } else {
            courseLimitCache.setCourseByCourse_id(course, course_id);

            throw new RuntimeException("该课程已经没有名额,不能再申请!");
        }

        return false;
    }

    public boolean applyDelete(String course_id, String request_user_id) {
        Student student = studentService.findByUser_id(request_user_id);
        Clazz clazz = clazzService.find(student.getClazz_id());

        check(clazz, request_user_id);
        check(clazz, request_user_id);

        boolean result = courseApplyService.deleteByCourse_idAndUser_id(course_id, request_user_id);

        return result;
    }

    public boolean applyAllDelete(String request_user_id) {
        return courseApplyService.deleteAll(request_user_id);
    }

    public void studentWhiteApplySave(String request_user_id) {
        List<Course> courseList = courseService.list(new Course(), 0, 0);

        for (Course course : courseList) {
            List<CourseStudent> courseStudentList = courseStudentService.list(course.getCourse_id(), CourseStudentType.WHITE.getKey());

            for (CourseStudent courseStudent : courseStudentList) {
                Student student = studentService.find(courseStudent.getStudent_id());

                courseApplyService.save(course.getCourse_id(), course, student.getUser_id());
            }
        }
    }

    public ExcelRender applyExport() {
        List<CourseApply> courseApplyListOrderByCourse_id = courseApplyService.listOrderByCourse_idAndCourse_timeAndStudent_number();
        List<CourseApply> courseApplyListOrderByGrade_idAndStudent_id = courseApplyService.listOrderByClazz_nameAndStudent_idAndCourse_time();

        List<Course> courseList = courseDao.list("", 0, 0);

        List<Teacher> teacherList = teacherService.list(new Teacher(), 0, 0);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFSheet sheet = wb.createSheet("选修情况汇总");

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("课程名称");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("上课时间");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("上课老师");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("上课地点");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("限制人数");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("已报人数");
        cell.setCellStyle(style);

        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);

            String course_time = "";
            switch (course.getCourse_time()) {
                case 17:
                    course_time = "星期一第七节";
                    break;
                case 27:
                    course_time = "星期二第七节";
                    break;
                case 28:
                    course_time = "星期二第八节";
                    break;
                case 47:
                    course_time = "星期四第七节";
                    break;
                case 48:
                    course_time = "星期四第八节";
                    break;
                case 56:
                    course_time = "星期五第六节";
                    break;
            }

            int count = courseApplyService.countByCourse_id(course.getCourse_id());

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(course.getCourse_name());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(course_time);
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(course.getCourse_teacher());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(course.getCourse_address());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(course.getCourse_apply_limit());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(count);
            cell.setCellStyle(style);
        }

        sheet = wb.createSheet("总-按科目");

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("班级");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("课程名称");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("上课时间");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("上课老师");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("上课地点");
        cell.setCellStyle(style);

        for(int i = 0; i < courseApplyListOrderByCourse_id.size(); i++) {
            CourseApply courseApply = courseApplyListOrderByCourse_id.get(i);

            String course_time = "";
            switch (courseApply.getInt("course_time")) {
                case 17:
                    course_time = "星期一第七节";
                    break;
                case 27:
                    course_time = "星期二第七节";
                    break;
                case 28:
                    course_time = "星期二第八节";
                    break;
                case 47:
                    course_time = "星期四第七节";
                    break;
                case 48:
                    course_time = "星期四第八节";
                    break;
                case 56:
                    course_time = "星期五第六节";
                    break;
            }

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(courseApply.getStr(Clazz.CLAZZ_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NUMBER));
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_SEX));
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(courseApply.getStr(Course.COURSE_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(course_time);
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(courseApply.getStr(Course.COURSE_TEACHER));
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(courseApply.getStr(Course.COURSE_ADDRESS));
            cell.setCellStyle(style);
        }

        sheet = wb.createSheet("总-按班级");

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("班级");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("课程名称");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("上课时间");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("上课老师");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("上课地点");
        cell.setCellStyle(style);

        for(int i = 0; i < courseApplyListOrderByGrade_idAndStudent_id.size(); i++) {
            CourseApply courseApply = courseApplyListOrderByGrade_idAndStudent_id.get(i);

            String teacher_name = courseApply.getStr(Course.COURSE_TEACHER);

            String course_time = "";
            switch (courseApply.getInt("course_time")) {
                case 17:
                    course_time = "星期一第七节";
                    break;
                case 27:
                    course_time = "星期二第七节";
                    break;
                case 28:
                    course_time = "星期二第八节";
                    break;
                case 47:
                    course_time = "星期四第七节";
                    break;
                case 48:
                    course_time = "星期四第八节";
                    break;
                case 56:
                    course_time = "星期五第六节";
                    break;
            }

            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(courseApply.getStr(Clazz.CLAZZ_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NUMBER));
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_SEX));
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(courseApply.getStr(Course.COURSE_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(course_time);
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(teacher_name);
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(courseApply.getStr(Course.COURSE_ADDRESS));
            cell.setCellStyle(style);
        }

        String course_id = "";
        int index = 0;
        for(int i = 0; i < courseApplyListOrderByCourse_id.size(); i++) {
            CourseApply courseApply = courseApplyListOrderByCourse_id.get(i);

            if(! course_id.equals(courseApply.getCourse_id())) {
                course_id = courseApply.getCourse_id();

                index = 0;

                sheet = wb.createSheet(i + 1 + "、" + courseApply.getStr(Course.COURSE_NAME));

                row = sheet.createRow(0);
                cell = row.createCell(0);
                cell.setCellValue("姓名");
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue("班级");
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue("学号");
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue("性别");
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue("课程名称");
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue("上课时间");
                cell.setCellStyle(style);
                cell = row.createCell(6);
                cell.setCellValue("上课老师");
                cell.setCellStyle(style);
                cell = row.createCell(7);
                cell.setCellValue("上课地点");
                cell.setCellStyle(style);
            }

            String teacher_name = courseApply.getStr(Course.COURSE_TEACHER);

            String course_time = "";
            switch (courseApply.getInt("course_time")) {
                case 17:
                    course_time = "星期一第七节";
                    break;
                case 27:
                    course_time = "星期二第七节";
                    break;
                case 28:
                    course_time = "星期二第八节";
                    break;
                case 47:
                    course_time = "星期四第七节";
                    break;
                case 48:
                    course_time = "星期四第八节";
                    break;
                case 56:
                    course_time = "星期五第六节";
                    break;
            }

            row = sheet.createRow(index + 1);
            cell = row.createCell(0);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(courseApply.getStr(Clazz.CLAZZ_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_NUMBER));
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(courseApply.getStr(Student.STUDENT_SEX));
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(courseApply.getStr(Course.COURSE_NAME));
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue(course_time);
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(teacher_name);
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(courseApply.getStr(Course.COURSE_ADDRESS));
            cell.setCellStyle(style);

            index++;
        }

        return new ExcelRender(wb, "选课信息");
    }

}