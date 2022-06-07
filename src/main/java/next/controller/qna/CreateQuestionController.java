package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.QuestionDao;
import next.model.Question;

@Slf4j
public class CreateQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            return jspView("redirect:/");
        }

        Question question = new Question(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents"));

        QuestionDao dao = new QuestionDao();
        Question createdQuestion = dao.insert(question);

        return jspView("redirect:/");
    }
}
