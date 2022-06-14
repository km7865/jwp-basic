package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

@Controller
public class HomeController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
