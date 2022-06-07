package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

@Slf4j
public class UpdateFormQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        Question question = new QuestionDao().findById(Long.parseLong(request.getParameter("questionId")));
        log.debug("Question: {}", question);
        if ( user == null || !user.getUserId().equals(question.getWriter())) {
            return jspView("redirect:/");
        }

        return jspView("/qna/updateForm.jsp").addObject("question", question);
    }
}
