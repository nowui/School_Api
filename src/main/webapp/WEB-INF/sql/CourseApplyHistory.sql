#namespace("course_apply_history")

  #sql("count")
    SELECT COUNT(*) FROM table_course_apply_history
    WHERE system_status = 1
    #if(course_apply_history_name)
      #set(course_apply_history_name = "%" + course_apply_history_name + "%")
      AND course_apply_history_name LIKE #p(course_apply_history_name)
    #end
  #end

  #sql("list")
    SELECT
    course_apply_history_id,
    course_id,
    user_id
    FROM table_course_apply_history
    WHERE system_status = 1
    AND user_id = #p(user_id)
    ORDER BY system_create_time DESC
  #end

  #sql("find")
    SELECT
    *
    FROM table_course_apply_history
    WHERE system_status = 1
    AND course_apply_history_id = #p(course_apply_history_id)
  #end

  #sql("delete")
    UPDATE table_course_apply_history SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE course_apply_history_id = #p(course_apply_history_id)
  #end

#end