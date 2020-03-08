package ru.otus;

import java.lang.reflect.Field;
import java.util.Collection;

public class DiyGson {
    public String toJson(Object object) {
        return parseObject(object);
    }

    String parsePrimitive(Object object) {
        if ( object == null ) return null;

        if ( isPrimitive(object) ){
            return object.toString();
        }

        if ( isCharacterOrString(object) ){
            return "\"" +
                    object.toString() +
                    "\"";
        }
        return null;
    }

    String parseObject(Object object){
        if (object == null) return null;

        if(isPrimitive(object) || isCharacterOrString(object)){
            return parsePrimitive(object);
        }

        if(isCollection(object)){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Object[] arrayObject= ((Collection) object).toArray();
            for (int j = 0; j < arrayObject.length; j++){
                sb.append(parseObject(arrayObject[j]));
                if (j < arrayObject.length-1){
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            stringBuilder.append("\"");
            stringBuilder.append(fields[i].getName());
            stringBuilder.append("\":");
            fields[i].setAccessible(true);
            Object objField = new Object();
            try {
                objField = fields[i].get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            stringBuilder.append(parseObject(objField));
            if (i < fields.length-1){
                stringBuilder.append(",");
            }
            fields[i].setAccessible(false);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    boolean isPrimitive(Object o) {
        Class<?> aClass = o.getClass();
        return  aClass == Double.class ||
                aClass == Float.class ||
                aClass == Long.class ||
                aClass == Integer.class ||
                aClass == Short.class ||
                aClass == Byte.class ||
                aClass == Boolean.class;
    }

    boolean isCharacterOrString(Object o) {
        Class<?> aClass = o.getClass();
        return aClass == Character.class ||
                aClass == String.class;
    }

    boolean isCollection(Object o) {
        Class<?> aClass = o.getClass();
        return Collection.class.isAssignableFrom(aClass);
    }
}
