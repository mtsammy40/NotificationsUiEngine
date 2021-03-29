package com.vsms.portal.data;

import com.vsms.portal.data.specifications.DataSpecification;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SearchCriteria<T> {
    private static final Logger LOGGER = LogManager.getLogger(SearchCriteria.class);

    private String key;
    private String operation;
    private Object value;
    private Class propertyClass;
    private Class<T> parentClass;

    public SearchCriteria(String key, String operation, Object value, Class<T> parentClass) throws Exception {
        this.key = key;
        this.operation = operation;
        this.value = value;
        setParentClass(parentClass);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isOrPredicate() {
        return true;
    }

    public Class getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass) {
        this.propertyClass = propertyClass;
    }

    public Class<T> getParentClass() {
        return parentClass;
    }

    public void setParentClass(Class<T> parentClass) throws Exception {
        validateCriteria(parentClass);
        this.parentClass = parentClass;
    }

    private void validateCriteria(Class<T> clazz) throws Exception {
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        Optional<Field> fieldResult = fieldList.stream().filter((field -> field.getName().equalsIgnoreCase(this.getKey()))).findFirst();
        if(fieldResult.isPresent()){
            Field field = fieldResult.get();
            this.setPropertyClass(field.getType());
            this.value = getTypedValue(value.toString(), propertyClass);
        } else {
            throw new ApiOperationException("Invalid key [ "+this.getKey()+" ] in search parameters", ApiStatus.INVALID_SEARCH_FIELD);
        }
    }

    private static <T> T getTypedValue(String value, Class<T> clazz) throws Exception {
        if (clazz == Long.class || clazz == long.class) {
            return (T) new Long(value);
        } else if (clazz == BigInteger.class) {
            return (T) new BigInteger(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else if (clazz.getAnnotation(Entity.class) != null) {
            // is JPA Entity class
            Object instance = clazz.newInstance();
            try {
                Field declaredField = clazz.getDeclaredField("id");
                Class<?> idClass = declaredField.getType();
                if (idClass == long.class || idClass == Long.class) {
                    declaredField.setAccessible(true);
                    declaredField.set(instance, Long.valueOf(value));
                    return (T) instance;
                } else {
                    LOGGER.error("INSTANTIATION OF CLASSES WITH ID ATTRIBUTE OF CLASS {} NOT SUPPORTED", idClass);
                    return null;
                }
            } catch (NoSuchFieldException
                    | SecurityException
                    | IllegalArgumentException
                    | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new Exception("CONVERSION OF TYPE " + String.class + " TO " + clazz + " IS UNSUPPORTED");
        }
    }
}
