package next.controller.unused;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.repository.AnswerRepository;
import next.repository.QuestionRepository;
import next.model.Answer;
import next.model.Question;

public class ShowQuestionController extends AbstractController {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public ShowQuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionRepository.findById(questionId);
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}
