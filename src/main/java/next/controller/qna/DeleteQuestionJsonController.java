package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.model.Result;

public class DeleteQuestionJsonController extends AbstractController {
    QnaService qnaService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}
