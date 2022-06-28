package core.mvc;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import core.HandlerMapping;
import core.di.factory.BeanFactory;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.BeanScanner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> mappings = Lists.newArrayList();
    private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();


    @Override
    public void init() throws ServletException {
        LegacyHandlerMapping lhm = new LegacyHandlerMapping();
        lhm.initMapping();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next");
        ahm.initialize();

        mappings.add(lhm);
        mappings.add(ahm);
        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        // 컨트롤러가 무엇인지 판단하고 그에 맞춰 실행하는 로직이 else if 로 추가되는 구조
        // 추상화가 필요함
        Object handler = getHandler(req);
        try {
            ModelAndView mav = execute(handler, req, resp);
            if (mav != null) {
                render(req, resp, mav);
            }
        } catch (Throwable e) {
            logger.error("Exception: {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        for (HandlerAdapter ha : handlerAdapters) {
            if (ha.supports(handler)) {
                return ha.handle(req, resp, handler);
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest req) throws ServletException {
        for (HandlerMapping hm : mappings) {
            Object handler = hm.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
