package com.vsms.portal.utils.enums;

public enum Strings {
    REQUEST_ATTRIBUTE_USER_KEY("_USER"),
    REQUEST_ATTRIBUTE_ERRORS_KEY("_AUTH_ERRORS"),
    SORT_PROPERTY_DEFAULT("_DEFAULT");

    String value;

    Strings(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
