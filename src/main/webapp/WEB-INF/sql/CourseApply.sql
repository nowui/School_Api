#namespace("course_apply")

  #sql("countByCourse_id")
    SELECT COUNT(*) FROM table_course_apply
    WHERE system_status = 1
    AND course_id = #p(course_id)
  #end

  #sql("countByUser_id")
    SELECT COUNT(*) FROM table_course_apply
    WHERE system_status = 1
    AND user_id = #p(user_id)
  #end

  #sql("countByCourse_idAndUser_id")
    SELECT COUNT(*) FROM table_course_apply
    WHERE system_status = 1
    AND course_id = #p(course_id)
    AND user_id = #p(user_id)
  #end

  #sql("countOneDayByUser_idAndCourse_time")
    SELECT COUNT(*)
    FROM table_course_apply
    LEFT JOIN table_course ON table_course.course_id = table_course_apply.course_id
    WHERE table_course_apply.system_status = 1
    AND user_id = #p(user_id)
    AND TRUNCATE(table_course.course_time, -1) = TRUNCATE(#p(course_time), -1)
  #end

  #sql("list")
    SELECT
    table_course.course_id,
    table_course.course_name,
    table_course.course_teacher,
    table_course.course_time,
    table_course.course_apply_limit,
    table_course_apply.user_id
    FROM table_course_apply
    LEFT JOIN table_course ON table_course.course_id = table_course_apply.course_id
    WHERE table_course_apply.system_status = 1
    AND user_id = #p(user_id)
    ORDER BY table_course.course_time, table_course_apply.system_create_time DESC
  #end

  #sql("listOrderByCourse_idAndCourse_timeAndStudent_number")
    SELECT table_course_apply.*, table_student.student_name, table_student.student_number, table_student.student_sex, table_clazz.clazz_name, table_course.course_name, table_course.course_time, table_course.course_teacher, table_course.course_address
    FROM table_course_apply
    LEFT JOIN table_student ON table_student.user_id = table_course_apply.user_id
    LEFT JOIN table_clazz ON table_clazz.clazz_id = table_student.clazz_id
    LEFT JOIN table_course ON table_course.course_id = table_course_apply.course_id
    WHERE table_course_apply.system_status = 1
    ORDER BY table_course.course_id, table_course.course_time, table_student.student_number ASC
  #end

  #sql("listOrderByClazz_nameAndStudent_idAndCourse_time")
    SELECT table_course_apply.*, table_student.student_name, table_student.student_number, table_student.student_sex, table_clazz.clazz_name, table_course.course_name, table_course.course_time, table_course.course_teacher, table_course.course_address
    FROM table_course_apply
    LEFT JOIN table_student ON table_student.user_id = table_course_apply.user_id
    LEFT JOIN table_clazz ON table_clazz.clazz_id = table_student.clazz_id
    LEFT JOIN table_course ON table_course.course_id = table_course_apply.course_id
    WHERE table_course_apply.system_status = 1
    ORDER BY table_clazz.clazz_name ASC, table_student.student_id DESC, table_course.course_time ASC
  #end

  #sql("find")
    SELECT
    *
    FROM table_course_apply
    WHERE system_status = 1
    AND course_apply_id = #p(course_apply_id)
  #end

  #sql("save")
    INSERT INTO table_course_apply (course_apply_id, course_id, user_id, system_create_user_id, system_create_time, system_update_user_id, system_update_time, system_status)
    SELECT #p(course_apply_id), #p(course_id), #p(user_id), #p(system_create_user_id), #p(system_create_time), #p(system_update_user_id), #p(system_update_time), #p(system_status) FROM dual
    WHERE NOT EXISTS (SELECT * FROM table_course_apply WHERE course_id = #p(course_id) AND user_id = #p(user_id) AND system_status = 1)
    AND NOT EXISTS (
                    SELECT COUNT(*) AS count, table_course_apply.course_id, table_course.course_apply_limit FROM table_course_apply
                    LEFT JOIN table_course ON table_course.course_id = table_course_apply.course_id
                    WHERE table_course_apply.course_id = #p(course_id)
                    AND table_course_apply.system_status = 1
                    HAVING count >= course_apply_limit
                   )
  #end

  #sql("deleteByCourse_idAndUser_id")
    UPDATE table_course_apply SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE system_status = 1
    AND course_id = #p(course_id)
    AND user_id = #p(user_id)
  #end

  #sql("deleteAll")
    UPDATE table_course_apply SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE system_status = 1
  #end

#end