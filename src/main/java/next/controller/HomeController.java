package next.controller;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.view.ModelAndView;

public class HomeController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();

        return jspView( "home.jsp").addObject("questions", questionDao.findAll());
    }
}
