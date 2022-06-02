package next.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        QuestionDao questionDao = new QuestionDao();
        req.setAttribute("questions", questionDao.findAll());
        req.setAttribute("users", userDao.findAll());
        return "home.jsp";
    }
}
