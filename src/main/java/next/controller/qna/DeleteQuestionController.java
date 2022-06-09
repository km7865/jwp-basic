package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
public class DeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();
    private QnaService qnaService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", questionDao.findById(questionId))
                    .addObject("answers", answerDao.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }


}
