package next.controller.user;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import next.view.ModelAndView;

public class ProfileController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        return jspView( "/user/profile.jsp").addObject("user", user);
    }
}
