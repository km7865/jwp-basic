package next.controller.qna;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import java.util.List;

public class QuestionUtils {
    protected static boolean isFit(Long questionId) {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        return (question.getCountOfComment() == 0 || isSame(answers, question.getWriter()));
    }

    protected static boolean isSame(List<Answer> answers, String writer) {
        for (Answer a : answers) {
            if (!(a.getWriter().equals(writer))) return false;
        }

        return true;
    }
}
