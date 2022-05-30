package next.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        log.debug("Request URI: {}", uri);
        Controller controller = rm.getController(uri);
        if (controller != null) {
            String path = controller.execute(req, resp);
            if (path.startsWith("redirect:")) {
                resp.sendRedirect(path.split(":")[1]);
            } else {
                req.getRequestDispatcher(path).forward(req, resp);
            }
        } else {
            resp.sendRedirect(getDefaultPath());
        }
    }

    private String getDefaultPath() {
        return "/index.jsp";
    }
}
