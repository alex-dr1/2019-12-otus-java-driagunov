package ru.otus;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiyGson {
    public String toJson(Object object) {
        return object == null ? null : parseObject(object);
    }

    String parseObject(Object object) {
//        {
//            "valueBoolean":true,  Boolean
//            "valueChar":"t",      Character
//            "valueShort":344, Short
//            "valueInteger":88888, Integer
//            "valueLong":10000000, Long
//            "valueString":"text123", String
//            "stringList":["one","two","three"], ListN
//            "longSet":[10,200,3000] SetN
//        }

        StringBuilder stringBuilder = new StringBuilder();
        if( isPrimitive( object, stringBuilder ) ){
            return stringBuilder.toString();
        }

        stringBuilder.append("{");

        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            int i = 5;
            declaredFields[i].setAccessible(true);
            Object o7 = declaredFields[i].get(object);
            declaredFields[i].setAccessible(false);
            stringBuilder.append("\"");
            stringBuilder.append(declaredFields[i].getName());
            stringBuilder.append("\":");
            //stringBuilder.append(o7.toString());
            isPrimitive(o7, stringBuilder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    boolean isPrimitiveWrapper(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Byte.class ||
                type == Boolean.class;
    }

    boolean isCharacterOrString(Class<?> type) {
        return type == Character.class || type == String.class;
    }

    boolean isPrimitive(Object o, StringBuilder sb){
        if(isPrimitiveWrapper(o.getClass())){
            sb.append(o.toString());
            return true;
        } else
            if (isCharacterOrString(o.getClass())){
                sb.append("\"");
                sb.append(o.toString());
                sb.append("\"");
                return true;
            } else
                return false;

    }

}
