package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.repository.QuestionRepository;
import next.model.Question;
import next.model.User;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class CreateQuestionController extends AbstractController {
    private QuestionRepository questionRepository;

    public CreateQuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Question question = new Question(user.getUserId(), request.getParameter("title"),
                request.getParameter("contents"));
        questionRepository.insert(question);
        return jspView("redirect:/");
    }

}
