package ru.otus;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Stream;

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


        Class<?> oClass = object.getClass();

        if(isPrimitive(oClass)){
            return object.toString();
        } else
            if (isCharacterOrString(oClass)){
                return "\"" + object.toString() + "\"";
            }
        /*Field[] declaredFields = oClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.print(field.getName());
            System.out.print(" - ");
            System.out.print(field.getType());
            System.out.print(" - ");
            System.out.println();

        }
        System.out.println(oClass.getName());

         */
        return "***";
    }

    static boolean isPrimitive(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Byte.class ||
                type == Boolean.class;
    }

    static boolean isCharacterOrString(Class<?> type) {
        return type == Character.class || type == String.class;
    }

}
