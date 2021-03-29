package com.vsms.portal.data.specifications;

public class FieldHolder {
    private Class<?> clazz;
    private String name;
    private String paramKey;

    public FieldHolder(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    @Override
    public String toString() {
        return "FieldHolder [clazz=" + clazz + ", name=" + name + ", paramKey=" + paramKey + "]";
    }
}
