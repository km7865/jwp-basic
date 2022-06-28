package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/qna/list");
    }
}
