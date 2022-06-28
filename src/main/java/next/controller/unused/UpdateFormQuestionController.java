package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.repository.QuestionRepository;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class UpdateFormQuestionController extends AbstractController {
    private QuestionRepository questionRepository;

    public UpdateFormQuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionRepository.findById(questionId);
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }
}
