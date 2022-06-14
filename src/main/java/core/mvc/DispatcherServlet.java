package core.mvc;

import java.io.IOException;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping rm;
    private AnnotationHandlerMapping ahm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();

        ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Controller controller = rm.findController(req.getRequestURI());
            if (controller != null) {
                render(req, resp, controller.execute(req, resp));
            } else {
                HandlerExecution he = ahm.getHandler(req);
                if (he == null) {
                    throw new ServletException("유효하지 않은 요청입니다.");
                }
                render(req, resp, he.handle(req, resp));
            }
        } catch (Throwable e) {
            throw new ServletException(e.getMessage());
        }
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
