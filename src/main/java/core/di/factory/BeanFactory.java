package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }
    public BeanFactory() {}

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public Map<Class<?>, Object> getBeans() {
        return beans;
    }

    public void initialize() {
        // 하위 패키지의 클래스 중 어노테이션 붙은 클래스 정보 가져오기
//        Reflections reflections = new Reflections("next");
//        preInstanticateBeans = Sets.newHashSet();
//        preInstanticateBeans.addAll(reflections.getTypesAnnotatedWith(Controller.class));
//        preInstanticateBeans.addAll(reflections.getTypesAnnotatedWith(Service.class));
//        preInstanticateBeans.addAll(reflections.getTypesAnnotatedWith(Repository.class));

        // @Inject 생성자를 가진 클래스를 찾고 DI
        // @Inject 생성자가 없다면 기본 생성자로 인스턴스화
        // 위 두 과정으로 클래스를 bean 으로 저장하기
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }

        for (Class<?> clazz : beans.keySet()) {
            logger.debug("Registered bean: {}", clazz.getName());
        }
    }

    private Object instantiateClass(Class<?> clazz) {
        try {
            Class<?> concreteCLass = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);

            Constructor<?> cons = BeanFactoryUtils.getInjectedConstructor(concreteCLass);
            if (cons == null) {
                logger.debug(" NoArgsConstructor Inject: {}", clazz.getName());
                beans.put(clazz, concreteCLass.getConstructor().newInstance());
            } else {
                beans.put(clazz, instantiateConstructor(cons));
            }
        } catch (ReflectiveOperationException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    private Object instantiateConstructor(Constructor<?> cons) {
        try {
            List<Object> args = Lists.newArrayList();
            Class<?>[] parameterTypes = cons.getParameterTypes();
            for (Class<?> clazz : parameterTypes) {
                clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
                Object bean = beans.get(clazz);
                if (bean == null) {
                    instantiateClass(clazz);
                }

                bean = beans.get(clazz);
                args.add(bean);
            }
            logger.debug(" -----ArgsConstructor inject in {}-----", cons.getName());
            for (Object o : args) {
                logger.debug(" param: {}", o.getClass());
            }
            logger.debug(" ----------Inject done!----------");

            return cons.newInstance(args.toArray());
        } catch (ReflectiveOperationException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

//    private void inject(Class<?> clazz) {
//        try {
//            clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
//
//            Constructor<?> cons = BeanFactoryUtils.getInjectedConstructor(clazz);
//            if (cons == null) {
//                logger.debug(" NoArgsConstructor Inject: {}", clazz.getName());
//                beans.put(clazz, clazz.getConstructor().newInstance());
//                return;
//            }
//
//            List<Object> args = Lists.newArrayList();
//            for (Class<?> c : cons.getParameterTypes()) {
//                c = BeanFactoryUtils.findConcreteClass(c, preInstanticateBeans);
//                Object bean = beans.get(c);
//                if (bean == null) {
//                    inject(c);
//                }
//
//                bean = beans.get(c);
//                args.add(bean);
//            }
//            logger.debug(" -----ArgsConstructor inject in {}-----", cons.getName());
//            for (Object o : args) {
//                logger.debug(" param: {}", o.getClass());
//            }
//            logger.debug(" ----------Inject done!----------");
//            beans.put(clazz, cons.newInstance(args.toArray()));
//        } catch (ReflectiveOperationException e) {
//            logger.error(e.getMessage());
//        }
//    }
}
