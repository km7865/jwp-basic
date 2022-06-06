package next.controller.user;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.view.JspView;
import next.view.ModelAndView;

public class ListUserController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView( "redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        return jspView("/user/list.jsp").addObject("users", userDao.findAll());
    }
}
