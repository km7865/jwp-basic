package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

@Slf4j
public class UpdateFormQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question question = new QuestionDao().findById(Long.parseLong(request.getParameter("questionId")));
        log.debug("Question: {}", question);

        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        if (UserSessionUtils.getUserFromSession(request.getSession()).isSameUser((User) request.getSession().getAttribute("user"))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        return jspView("/qna/updateForm.jsp").addObject("question", question);
    }
}
