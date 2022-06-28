package next.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.repository.QuestionRepository;

@Controller
public class HomeController extends AbstractController {
    private QuestionRepository questionRepository;

    @Inject
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionRepository.findAll());
    }
}
