package core.ref;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        Constructor<?>[] cons = clazz.getDeclaredConstructors();

        logger.debug("------------------------fields------------------------");
        for (Field f : fields) {
            logger.debug(f.getName());
        }

        logger.debug("------------------------methods------------------------");

        for (Method m : methods) {
            logger.debug(m.getName());
        }

        logger.debug("------------------------constructors------------------------");

        for (Constructor<?> c : cons) {
            logger.debug(c.getName());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        try {
            Constructor<User> cons = clazz.getDeclaredConstructor(String.class, String.class, String.class,String.class);
            User user = cons.newInstance("1", "2", "3", "4");
            logger.debug("user: {}", user);
        } catch (ReflectiveOperationException e) {
            logger.error(e.getMessage());
        }
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        try {
            Constructor<Student> cons = clazz.getDeclaredConstructor();
            Field[] fields = clazz.getDeclaredFields();
            Student student = cons.newInstance();
            logger.debug("Before student name: {}, age: {}", student.getName(), student.getAge());
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("name")) {
                    f.set(student, "private student");
                } else if (f.getName().equals("age")) {
                    f.set(student, 9999);
                }
            }
            logger.debug("After student name: {}, age: {}", student.getName(), student.getAge());

        } catch (ReflectiveOperationException e) {
            logger.error(e.getMessage());
        }
    }
}
