package core.ref;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method method1 = clazz.getDeclaredMethod("test1", null);
        Method method2 = clazz.getDeclaredMethod("test2", null);
        Method method3 = clazz.getDeclaredMethod("test3", null);
        method1.invoke(clazz.getConstructor(null).newInstance());
        method2.invoke(clazz.getConstructor(null).newInstance());
        method3.invoke(clazz.getConstructor(null).newInstance());
    }
}
