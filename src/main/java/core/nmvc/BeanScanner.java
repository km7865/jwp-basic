package core.nmvc;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.factory.BeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanScanner {
    Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getBeans() {
        Set<Class<?>> annotated = Sets.newHashSet();
        annotated.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        annotated.addAll(reflections.getTypesAnnotatedWith(Service.class));
        annotated.addAll(reflections.getTypesAnnotatedWith(Repository.class));

        // return instantiateControllers(annotated);
        BeanFactory beanFactory = new BeanFactory(annotated);
        beanFactory.initialize();
        return beanFactory.getBeans();
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = new HashMap<>();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                // @Controller 어노테이션에 아무 것도 명시하지 않은 상태에서 value() 호출하면 NullPointerException 을 발생시킬 수 있음
                // DI Framework 로 리팩토링을 하면 getDeclaredConstructor(Object... args).newInstance() 의 형태로 구현해야 함
                controllers.put(clazz, clazz.getDeclaredConstructor(null).newInstance());
            }
        } catch (ReflectiveOperationException e) {
            log.error(e.getMessage());
        }
        return controllers;
    }

}
