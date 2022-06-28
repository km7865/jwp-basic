package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.repository.QuestionRepository;

public class ApiListQuestionController extends AbstractController {
    private QuestionRepository questionRepository;

    public ApiListQuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jsonView().addObject("questions", questionRepository.findAll());
    }
}
