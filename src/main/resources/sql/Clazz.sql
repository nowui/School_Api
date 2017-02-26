#namespace("clazz")

  #sql("count")
    SELECT COUNT(*) FROM table_clazz
    WHERE system_status = 1
    #if(clazz_name)
      #set(clazz_name = "%" + clazz_name + "%")
      AND clazz_name LIKE #p(clazz_name)
    #end
  #end

  #sql("list")
    SELECT
    clazz_id,
    clazz_name,
    clazz_course_apply_limit
    FROM table_clazz
    WHERE system_status = 1
    #if(clazz_name)
      #set(clazz_name = "%" + clazz_name + "%")
      AND clazz_name LIKE #p(clazz_name)
    #end
    ORDER BY clazz_sort ASC, system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_clazz
    WHERE system_status = 1
    AND clazz_id = #p(clazz_id)
  #end

  #sql("delete")
    UPDATE table_clazz SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE clazz_id = #p(clazz_id)
  #end

  #sql("deleteAll")
    UPDATE table_clazz SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE system_status = 1
  #end

#end