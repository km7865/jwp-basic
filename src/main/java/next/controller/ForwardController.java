package next.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null.");
        }
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return forwardUrl;
    }
}
