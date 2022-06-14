package core.nmvc;

import core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ControllerScanner {
    private Map<Class<?>, Object> controllers = new HashMap<>();

    public void init(String basePackage) {
        try {
            log.debug("basePackage: {}", basePackage);
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
            Iterator<Class<?>> iter = annotated.iterator();
            while (iter.hasNext()) {
                Class clazz = iter.next();
                controllers.put(clazz, clazz.getDeclaredConstructor(null).newInstance());
                log.debug("class: {} {}", clazz.getName(), controllers.get(clazz));
            }

        } catch (ReflectiveOperationException e) {
            log.error(e.getMessage());
        }
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }
}
