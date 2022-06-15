package core.nmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class HandlerExecution {
    private Method method;
    private Object instance;

    public HandlerExecution(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            return (ModelAndView) method.invoke(instance, request, response);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error("{} method invoke fail. error message : {}", method.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
