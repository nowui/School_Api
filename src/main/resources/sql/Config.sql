#namespace("config")

  #sql("count")
    SELECT COUNT(*) FROM table_config
    WHERE system_status = 1
  #end

  #sql("list")
    SELECT
    config_id
    FROM table_config
    WHERE system_status = 1
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("find")
    SELECT
    *
    FROM table_config
    WHERE system_status = 1
  #end

  #sql("update")
    UPDATE table_config SET
    config_apply_start_time = #p(config_apply_start_time),
    config_apply_end_time = #p(config_apply_end_time),
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time)
  #end

#end