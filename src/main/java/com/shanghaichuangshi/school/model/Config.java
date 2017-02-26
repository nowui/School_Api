package com.shanghaichuangshi.school.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Config extends Model<Config> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String CONFIG_ID = "config_id";

    @Column(type = ColumnType.VARCHAR, length = 19, comment = "")
    public static final String CONFIG_APPLY_START_TIME = "config_apply_start_time";

    @Column(type = ColumnType.VARCHAR, length = 19, comment = "")
    public static final String CONFIG_APPLY_END_TIME = "config_apply_end_time";

    
    public String getConfig_id() {
        return getStr(CONFIG_ID);
    }

    public void setConfig_id(String config_id) {
        set(CONFIG_ID, config_id);
    }

    public String getConfig_apply_start_time() {
        return getStr(CONFIG_APPLY_START_TIME);
    }

    public void setConfig_apply_start_time(String config_apply_start_time) {
        set(CONFIG_APPLY_START_TIME, config_apply_start_time);
    }

    public String getConfig_apply_end_time() {
        return getStr(CONFIG_APPLY_END_TIME);
    }

    public void setConfig_apply_end_time(String config_apply_end_time) {
        set(CONFIG_APPLY_END_TIME, config_apply_end_time);
    }
}