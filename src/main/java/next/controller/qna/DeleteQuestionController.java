package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

public class DeleteQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        ModelAndView mav = jspView("redirect:/");
        if (QuestionUtils.isFit(questionId)) {
            try {
                new QuestionDao().remove(questionId);
                mav.addObject("result", Result.ok());
            } catch (DataAccessException e) {
                mav.addObject("result", Result.fail(e.getMessage()));
            }
        }

        return mav;
    }


}
