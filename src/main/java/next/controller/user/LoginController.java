package next.controller.user;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import next.view.ModelAndView;

public class LoginController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            return jspView( "/user/login.jsp").addObject("loginFailed", true);
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return jspView("redirect:/");
        } else {
            req.setAttribute("loginFailed", true);
            return jspView("/user/login.jsp").addObject("loginFailed", true);
        }
    }
}
