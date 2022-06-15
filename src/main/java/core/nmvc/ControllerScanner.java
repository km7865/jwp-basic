package core.nmvc;

import core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ControllerScanner {
    Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(annotated);
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = new HashMap<>();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
//                Controller c = clazz.getAnnotation(Controller.class);
//                log.debug("{}'s annotation: {}", clazz.getName(), c.value());
                // @Controller 어노테이션에 아무 것도 명시하지 않은 상태에서 value() 호출하면 NullPointerException 을 발생시킬 수 있음
                controllers.put(clazz, clazz.getDeclaredConstructor(null).newInstance());
                log.debug("class: {} {}", clazz.getName(), controllers.get(clazz));
            }
        } catch (ReflectiveOperationException e) {
            log.error(e.getMessage());
        }
        return controllers;
    }

}
