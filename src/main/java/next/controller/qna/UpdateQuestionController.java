package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.QuestionDao;
import next.model.Question;

@Slf4j
public class UpdateQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question question = new Question(
                Long.parseLong(request.getParameter("questionId")),
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents"));
        log.debug("Update Question: {}", question);
        new QuestionDao().update(question);

        return jspView("redirect:/");
    }
}
