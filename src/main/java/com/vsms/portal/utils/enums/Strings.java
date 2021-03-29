package com.vsms.portal.utils.enums;

public enum Strings {
    REQUEST_ATTRIBUTE_USER_KEY("_USER");

    String value;

    Strings(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
