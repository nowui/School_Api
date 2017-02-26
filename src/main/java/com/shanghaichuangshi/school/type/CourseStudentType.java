package com.shanghaichuangshi.school.type;

public enum CourseStudentType {

    WHITE("WHITE", "白名单"),
    BLACK("BLACK", "黑名单");

    private String key;
    private String value;

    private CourseStudentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
