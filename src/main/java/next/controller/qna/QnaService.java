package next.controller.qna;


import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;

import java.util.List;

/*
 * DeleteQuestionController, DeleteQuestionJsonController 의 중복된 코드를 해결하기 위해 생성하는 클래스
 * 컨트롤러에서 발생하는 중복을 제거하고 컨트롤러의 역할 분리 등을 목적으로 서비스라는 클래스를 추가해 담당하도록 발전해왔다.
 */
public class QnaService {
    public static QnaService qnaService;

    private QnaService() {
    }

    public static QnaService getInstance() {
        if (qnaService == null) {
            qnaService = new QnaService();
        }
        return qnaService;
    }

    public void deleteQuestion(long questionId, User user) {
        user.isSameUser(
        ModelAndView mav = jspView("redirect:/");
        Result result = QuestionUtils.isFit(questionId);
    }

    protected Result isFit(Long questionId) {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if (question.getCountOfComment() != 0 && isSame(answers, question.getWriter())) {
            return Result.fail("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
        }

        return Result.ok();
    }

    protected boolean isSame(List<Answer> answers, String writer) {
        for (Answer a : answers) {
            if (!(a.getWriter().equals(writer))) return false;
        }

        return true;
    }
}
