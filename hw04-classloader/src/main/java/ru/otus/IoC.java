package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class IoC {
    static TestLoggingInterface createMyClass(TestLogging proxyClass) {
        InvocationHandler handler = new DemoInvocationHandler(proxyClass);
        return (TestLoggingInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface loggingInterface;
        private final Set<String> methodNameAnnotationLog = new HashSet<>();

        DemoInvocationHandler(TestLoggingInterface loggingInterface) {
            this.loggingInterface = loggingInterface;
            Class<?> aClass = loggingInterface.getClass();
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method: methods){
                if(method.isAnnotationPresent(Log.class)){
                    methodNameAnnotationLog.add(method.getName());
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(methodNameAnnotationLog.contains(method.getName())){
                System.out.print("executed method:" + method.getName());
                System.out.println(", param:" + Arrays.toString(args));
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
