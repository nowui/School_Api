package com.shanghaichuangshi.school.service;

import com.jfinal.upload.UploadFile;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.school.dao.StudentDao;
import com.shanghaichuangshi.school.model.Clazz;
import com.shanghaichuangshi.school.model.Student;
import com.shanghaichuangshi.service.AuthorizationService;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService extends Service {

    private static final StudentDao studentDao = new StudentDao();

    private static final UserService userService = new UserService();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private static final ClazzService clazzService = new ClazzService();

    public int count(Student student) {
        return studentDao.count(student.getStudent_name(), student.getClazz_id());
    }

    public List<Student> list(Student student, int m, int n) {
        return studentDao.list(student.getStudent_name(), student.getClazz_id(), m, n);
    }

    public Student find(String student_id) {
        return studentDao.find(student_id);
    }

    public Student findByUser_id(String user_id) {
        return studentDao.findByUser_id(user_id);
    }

    public Student save(Student student, User user, String request_user_id) {
        Student s = studentDao.save(student, request_user_id);

        String user_id = userService.saveByUser_accountAndUser_passwordAndObject_idAndUser_type(student.getStudent_number(), user.getUser_password(), s.getStudent_id(), UserType.STUDENT.getKey(), request_user_id);

        studentDao.updateByStudent_idAndUser_id(s.getStudent_id(), user_id, request_user_id);

        return s;
    }

    public boolean update(Student student, User user, String request_user_id) {
        boolean result = studentDao.update(student, request_user_id);

        userService.updateByObject_idAndUser_accountAndUser_type(student.getStudent_id(), student.getStudent_number(), UserType.STUDENT.getKey(), request_user_id);

        userService.updateByObject_idAndUser_passwordAndUser_type(student.getStudent_id(), user.getUser_password(), UserType.STUDENT.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Student student, String request_user_id) {
        return studentDao.delete(student.getStudent_id(), request_user_id);
    }

    public boolean deleteAll(String request_user_id) {
        boolean result = studentDao.deleteAll(request_user_id);

        userService.deleteByUser_type(UserType.STUDENT.getKey(), request_user_id);

        return result;
    }

    public Map<String, Object> login(User user, String platform, String version, String ip_address, String request_user_id) {
        User u = userService.findByUser_accountAndUser_passwordAndUser_type(user.getUser_account(), user.getUser_password(), UserType.STUDENT.getKey());

        Student student = studentDao.find(u.getObject_id());

        String token = authorizationService.saveByUser_id(u.getUser_id(), platform, version, ip_address, request_user_id);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Student.STUDENT_NAME, student.getStudent_name());
        resultMap.put(Clazz.CLAZZ_NAME, student.getStr(Clazz.CLAZZ_NAME));
        resultMap.put(Constant.TOKEN.toLowerCase(), token);

        return resultMap;
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

                for(int i = 1; i <= trLength; i++) {
                    HSSFRow row = sheet.getRow(i);

                    HSSFCell gradeCell = row.getCell(0);
                    gradeCell.setCellType(CellType.STRING);

                    HSSFCell numberCell = row.getCell(1);
                    numberCell.setCellType(CellType.STRING);

                    HSSFCell nameCell = row.getCell(2);
                    nameCell.setCellType(CellType.STRING);

                    HSSFCell sexCell = row.getCell(3);
                    sexCell.setCellType(CellType.STRING);

                    HSSFCell passwordCell = row.getCell(4);
                    passwordCell.setCellType(CellType.STRING);

                    String student_clazz = gradeCell.getStringCellValue();
                    String student_number = gradeCell.getStringCellValue() + (numberCell.getStringCellValue().length() == 1 ? "0" : "") + numberCell.getStringCellValue();
                    String student_name = nameCell.getStringCellValue();
                    String student_sex = sexCell.getStringCellValue();
                    String user_password = passwordCell.getStringCellValue();

                    if(Util.isNullOrEmpty(student_number) || Util.isNullOrEmpty(student_name) || Util.isNullOrEmpty(student_sex)) {

                    } else {
                        String clazz_id = "";

                        for(Clazz clazz : clazzList) {
                            if(clazz.getClazz_name().equals(student_clazz)) {
                                clazz_id = clazz.getClazz_id();

                                break;
                            }
                        }

                        if(clazz_id != "") {
                            User user = new User();
                            user.setUser_password(user_password);

                            Student student = new Student();
                            student.setClazz_id(clazz_id);
                            student.setStudent_name(student_name);
                            student.setStudent_number(student_number);
                            student.setStudent_sex(student_sex);
                            save(student, user, request_user_id);
                        }
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

}