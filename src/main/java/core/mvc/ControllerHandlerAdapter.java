package core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return ((Controller) handler).execute(req, resp);
    }
}
