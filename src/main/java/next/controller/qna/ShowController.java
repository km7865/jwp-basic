package next.controller.qna;

import core.mvc.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.view.JspView;
import next.view.ModelAndView;

public class ShowController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        req.setAttribute("question", questionDao.findById(questionId));
        req.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
        return new ModelAndView(new JspView( "/qna/show.jsp"));
    }
}
