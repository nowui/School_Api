#namespace("student")

  #sql("count")
    SELECT COUNT(*) FROM table_student
    WHERE system_status = 1
    #if(student_name)
      #set(student_name = "%" + student_name + "%")
      AND student_name LIKE #p(student_name)
    #end
    #if(clazz_id)
      AND clazz_id = #p(clazz_id)
    #end
  #end

  #sql("list")
    SELECT
    table_student.student_id,
    table_student.student_name,
    table_student.student_number,
    table_clazz.clazz_name
    FROM table_student
    LEFT JOIN table_clazz ON table_clazz.clazz_id = table_student.clazz_id
    WHERE table_student.system_status = 1
    #if(student_name)
      #set(student_name = "%" + student_name + "%")
      AND table_student.student_name LIKE #p(student_name)
    #end
    #if(clazz_id)
      AND table_student.clazz_id = #p(clazz_id)
    #end
    ORDER BY table_student.system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    table_student.*,
    table_clazz.clazz_name
    FROM table_student
    LEFT JOIN table_clazz ON table_clazz.clazz_id = table_student.clazz_id
    WHERE table_student.system_status = 1
    AND table_student.student_id = #p(student_id)
  #end

  #sql("findByUser_id")
    SELECT
    *
    FROM table_student
    WHERE system_status = 1
    AND user_id = #p(user_id)
  #end

  #sql("updateByStudent_idAndUser_id")
    UPDATE table_student SET
    user_id = #p(user_id),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE student_id = #p(student_id)
  #end

  #sql("delete")
    UPDATE table_student SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = #p(system_status)
    WHERE student_id = #p(student_id)
  #end

  #sql("deleteAll")
    UPDATE table_student SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE system_status = 1
  #end

#end