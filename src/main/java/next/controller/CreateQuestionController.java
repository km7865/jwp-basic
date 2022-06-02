package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.QuestionDao;
import next.model.Question;

import java.time.LocalDateTime;

@Slf4j
public class CreateQuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Question question = new Question(
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                LocalDateTime.now());

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);
        log.debug("Question: {}", question);
        return "redirect:/";
    }
}
