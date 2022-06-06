package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.AnswerDao;
import next.model.Answer;
import next.view.JsonView;
import next.view.ModelAndView;

@Slf4j
public class AddAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("Answer: {}", answer);
        AnswerDao dao = new AnswerDao();
        Answer savedAnswer = dao.insert(answer);

        return jsonView().addObject("answer", savedAnswer);
    }
}
