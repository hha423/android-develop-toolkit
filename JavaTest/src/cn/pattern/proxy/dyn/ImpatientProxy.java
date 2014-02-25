package cn.pattern.proxy.dyn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ImpatientProxy implements InvocationHandler {
    private Object obj;

    public ImpatientProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        long t1 = System.currentTimeMillis();
        result = method.invoke(obj, args);
        long t2 = System.currentTimeMillis();
        System.out.println("times:" + (t2 - t1));
        return result;
    }

    public static Object newInstance(Object obj) {
        ClassLoader loader = obj.getClass().getClassLoader();
        Class<?>[] classes = obj.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, classes, new ImpatientProxy(obj));
    }

    /**
     * 动态代理测试
     * 
     * @param args
     */
    public static void main(String[] args) {
        Worker worker = new ManWorker();
        worker = (Worker) ImpatientProxy.newInstance(worker);//更新

        worker.working(1000); // 动态代理接口去做
        worker.sleep(); // 动态代理接口去做
    }
}
