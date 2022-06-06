package next.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.support.JsonObject;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonView implements View {
    private Map<String, ?> createModel(HttpServletRequest req) {
        Enumeration<String> names = req.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            model.put(name, req.getAttribute(name));
        }
        return model;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String value = new ObjectMapper().writeValueAsString(model);

        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(value);
        log.debug("Result Json: {}", value);
    }
}
