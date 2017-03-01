#namespace("course")

  #sql("count")
    SELECT COUNT(*) FROM table_course
    WHERE system_status = 1
    #if(course_name)
      #set(course_name = "%" + course_name + "%")
      AND course_name LIKE #p(course_name)
    #end
  #end

  #sql("list")
    SELECT
    course_id,
    course_name,
    course_teacher,
    course_address,
    course_time,
    course_apply_limit
    FROM table_course
    WHERE system_status = 1
    #if(course_name)
      #set(course_name = "%" + course_name + "%")
      AND course_name LIKE #p(course_name)
    #end
    ORDER BY course_time, system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("listByUser_id")
    SELECT
    course_id,
    course_name,
    course_teacher,
    course_time,
    course_apply_limit
    FROM table_course
    WHERE system_status = 1
    AND clazz_id LIKE (SELECT CONCAT('%', clazz_id, '%') FROM table_student WHERE user_id = #p(user_id))
    ORDER BY course_time, system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_course
    WHERE system_status = 1
    AND course_id = #p(course_id)
  #end

  #sql("delete")
    UPDATE table_course SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE course_id = #p(course_id)
  #end

  #sql("deleteAll")
    UPDATE table_course SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE system_status = 1
  #end

#end