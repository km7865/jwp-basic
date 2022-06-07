package next.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private String viewName;

    public JspView(String viewName) {
        if (viewName == null) {
            throw new NullPointerException("viewName is null.");
        }
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        Set<String> keys = model.keySet();
        for (String key : keys) {
            req.setAttribute(key, model.get(key));
            log.debug("model.get: {}", model.get(key));
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
