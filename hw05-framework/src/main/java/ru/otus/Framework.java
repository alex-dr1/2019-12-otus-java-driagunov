package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Framework {
    public static void run(Class<?> aClass ){

        int tested = 0;
        int passed = 0;

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method method: aClass.getDeclaredMethods()){
            if (method.isAnnotationPresent(Before.class)){
                beforeMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)){
                afterMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
            }
        }

        for (Method testMethod: testMethods){
            System.out.println("---------------");
            Object object = null;
            try {
                object = aClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (object != null){
                try {
                    tested++;
                    for (Method beforeMethod : beforeMethods) {
                        callMethod(object, beforeMethod);
                    }
                    callMethod(object, testMethod);
                    for (Method afterMethod : afterMethods) {
                        callMethod(object, afterMethod);
                    }
                    passed++;
                }  catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Failed:" + e);
                }
            }
        }

        System.out.println("-----TOTAL-----");
        System.out.println("Tested:" + tested + " Passed:" + passed + " Missed:" + (tested - passed));
    }

    public static void callMethod(Object object, Method method) throws InvocationTargetException, IllegalAccessException {
        if(method.canAccess(object)) {
            method.invoke(object);
        }
    }
}
