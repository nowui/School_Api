#namespace("teacher")

  #sql("count")
    SELECT COUNT(*) FROM table_teacher
    WHERE system_status = 1
    #if(teacher_name)
      #set(teacher_name = "%" + teacher_name + "%")
      AND teacher_name LIKE #p(teacher_name)
    #end
  #end

  #sql("list")
    SELECT
    teacher_id,
    teacher_name
    FROM table_teacher
    WHERE system_status = 1
    #if(teacher_name)
      #set(teacher_name = "%" + teacher_name + "%")
      AND teacher_name LIKE #p(teacher_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    table_teacher.*,
    table_user.user_account
    FROM table_teacher
    LEFT JOIN table_user ON table_user.user_id = table_teacher.user_id
    WHERE table_teacher.system_status = 1
    AND table_teacher.teacher_id = #p(teacher_id)
  #end

  #sql("updateByTeacher_idAndUser_id")
    UPDATE table_teacher SET
    user_id = #p(user_id),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
    WHERE teacher_id = #p(teacher_id)
  #end

  #sql("delete")
    UPDATE table_teacher SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE teacher_id = #p(teacher_id)
  #end

#end