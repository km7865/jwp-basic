package core;

import jakarta.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    Object getHandler(HttpServletRequest req);
}
