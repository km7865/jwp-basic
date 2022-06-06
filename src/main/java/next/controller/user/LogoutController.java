package next.controller.user;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.view.JspView;
import next.view.ModelAndView;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return jspView("redirect:/");
    }
}
