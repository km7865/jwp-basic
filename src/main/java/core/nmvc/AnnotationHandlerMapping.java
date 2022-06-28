package core.nmvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import core.HandlerMapping;
import core.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.RequestMethod;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private ControllerScanner cs;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        /*
         * next.controller.Controller 들의 method 와 url 을 매핑하자
         * ControllerScanner 를 통해 @Controller 어노테이션이 적용된 클래스 찾아 Map<Classs<?>, Object> 형태로 저장
         * 저장된 클래스 리스트를 순회하며 @RequestMapping 어노테이션이 적용된 메서드 찾기
         * 찾은 메서드로 RequestMapping 의 value, http_method 를 짝으로 HandlerKey 인스턴스 생성
         * HandlerExecution(Method, Object) 생성
         * HandlerKey, HandlerExecution 맵에 저장함으로써 매핑 완료
         *
         * DI(의존성 주입) Framework 로 변경하게 되면 정상적으로 작동할 수 없기 때문에 DI를 해줘야함
         *
         */

        cs = new ControllerScanner(basePackage);

        Map<Class<?>, Object> controllers = cs.getControllers();
        Set<Method> methods = getRequestMethods(controllers.keySet());
        for (Method m : methods) {
            RequestMapping rm = m.getAnnotation(RequestMapping.class);
            HandlerKey handlerKey = createHandlerKey(rm);
            log.debug("handlerKey: {}", handlerKey);
            handlerExecutions.put(handlerKey, new HandlerExecution(m, controllers.get(m.getDeclaringClass())));
        }
    }

    private Set<Method> getRequestMethods(Set<Class<?>> classes) {
        Set<Method> methods = new HashSet<>();
        for (Class<?> clazz : classes) {
            methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }

        return methods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }
}
