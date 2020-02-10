package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.*;

class IoC {
    static TestLoggingInterface createMyClass(TestLogging proxyClass) {
        InvocationHandler handler = new DemoInvocationHandler(proxyClass);
        return (TestLoggingInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface loggingInterface;
        private final Map<Integer, String> methodAnnotationLog = new HashMap<>();

        DemoInvocationHandler(TestLoggingInterface loggingInterface) {
            this.loggingInterface = loggingInterface;
            Class<?> aClass = loggingInterface.getClass();
            Method[] methods = aClass.getDeclaredMethods();

            for (Method method: methods){
                if(method.isAnnotationPresent(Log.class)){
                    methodAnnotationLog.put(Arrays.hashCode(method.getGenericParameterTypes()), method.getName());
                }
            }

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(methodAnnotationLog.containsKey(Arrays.hashCode(method.getGenericParameterTypes()))){
                boolean methodLog = methodAnnotationLog.get(Arrays.hashCode(method.getGenericParameterTypes())).equals(method.getName());
                if(methodLog){
                    System.out.print("executed method:" + method.getName());
                    System.out.println(", param:" + Arrays.toString(args));
                }
            }
            return method.invoke(loggingInterface, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + loggingInterface +
                    '}';
        }
    }

}
