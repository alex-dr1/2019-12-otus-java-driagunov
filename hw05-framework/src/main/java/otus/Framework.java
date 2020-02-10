package otus;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Framework {
    public static Integer tested = 0;
    public static Integer passed = 0;
    public static void main(String[] args) {
        run(TestClass.class);
    }

    public static void run(Class<?> aClass ){

        Object object = null;
        try {
            object = aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (object != null){
            callMethod(object, Before.class);
            callMethod(object, Test.class);
            callMethod(object, After.class);
        }

        System.out.println("----TOTAL----");
        System.out.println("tested:" + tested + " passed:" + passed + " missed:" + (tested - passed));
    }

    public static void callMethod(Object object, Class<? extends Annotation> annotationClass){
        for (Method method: object.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(annotationClass)){
                if(method.canAccess(object)){
                    tested++;
                    try {
                        method.invoke(object);
                        passed++;
                    } catch (Exception ex) {
                        System.out.println(method.getName() + " failed: " + ex);
                    }
                }

            }

        }

    }
}
