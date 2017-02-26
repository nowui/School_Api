#namespace("course_student")

  #sql("count")
    SELECT COUNT(*) FROM table_course_student
    WHERE system_status = 1
    AND course_id = #p(course_id)
    AND student_id = #p(student_id)
  #end

  #sql("list")
    SELECT course_student_id, table_course_student.student_id, clazz_name, student_name, student_number
    FROM table_course_student
    LEFT JOIN table_student ON table_student.student_id = table_course_student.student_id
    LEFT JOIN table_clazz ON table_clazz.clazz_id = table_student.clazz_id
    WHERE table_course_student.system_status = 1
    AND course_id = #p(course_id)
    AND course_student_type = #p(course_student_type)
    ORDER BY table_course_student.system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_course_student
    WHERE system_status = 1
    AND course_student_id = #p(course_student_id)
  #end

  #sql("delete")
    UPDATE table_course_student SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE course_student_id = #p(course_student_id)
  #end

#end