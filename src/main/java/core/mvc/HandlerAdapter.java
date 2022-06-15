package core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
