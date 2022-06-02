package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.model.Answer;

import java.util.List;

/*
 * 질문의 상세정보 관리
 * questionId 가 있어야 answers 를 조회하여 출력할 수 있음
 * /questions/show/${question.id}
 *
 */
public class QuestionShowController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Answer> answers = new AnswerDao().findByQuestionId(Long.parseLong(req.getParameter("questionId")));
        req.setAttribute("answers", answers);
        return "qna/show.jsp";
    }
}
