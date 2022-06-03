package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import java.util.List;

/*
 * 질문의 상세정보 관리
 * questionId 가 있어야 answers 를 조회하여 출력할 수 있음
 * /questions/show?questionId={question.id}
 *
 */
public class QuestionShowController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = new QuestionDao().findByQuestionId(questionId);
        List<Answer> answers = new AnswerDao().findByQuestionId(questionId);

        req.setAttribute("question", question);
        req.setAttribute("answers", answers);
        req.setAttribute("length", answers.size());
        return "/qna/show.jsp";
    }
}
