package next.controller.qna;


import core.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;

/*
 * DeleteQuestionController, DeleteQuestionJsonController 의 중복된 코드를 해결하기 위해 생성하는 클래스
 * 컨트롤러에서 발생하는 중복을 제거하고 컨트롤러의 역할 분리 등을 목적으로 서비스라는 클래스를 추가해 담당하도록 발전해왔다.
 */
@Slf4j
public class QnaService {
    public static QnaService qnaService;

    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();

    private QnaService() {
    }

    public static QnaService getInstance() {
        if (qnaService == null) {
            qnaService = new QnaService();
        }
        return qnaService;
    }

    public void deleteQuestion(long questionId, User user) throws Exception {
        Question question = questionDao.findById(questionId);
        log.debug("{} {}", question.getWriter(), user.getUserId());

        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        if (!question.getWriter().equals(user.getUserId())) {
            throw new CannotDeleteException("다른 사용자가 쓴 글은 삭제할 수 없습니다.");
        }

        if (!isFit(questionId)) {
            throw new CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
        }

        questionDao.delete(questionId);
    }

    protected boolean isFit(Long questionId) {
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if (question.getCountOfComment() != 0 && isSame(answers, question.getWriter())) {
            return false;
        }

        return true;
    }

    protected boolean isSame(List<Answer> answers, String writer) {
        for (Answer a : answers) {
            if (!(a.getWriter().equals(writer))) return false;
        }

        return true;
    }
}
