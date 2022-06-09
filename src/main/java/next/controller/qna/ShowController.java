package next.controller.qna;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;


/*
 * 스택 영역은 각 메소드가 실행될 때 메소드의 인자, 로컬 변수 등을 관리 (즉, 스레드마다 서로 다른 스택 영역)
 * 힙 영역은 클래스의 인스턴스 상태 데이터를 관리 (즉, 스레드가 서로 공유할 수 있는 영역)
 * 멀티 스레드 환경에서 스레드1이 execute 메소드를 실행할 때, findById()로 question1을 변수에 저장하여 사용한다고 가정하자.
 * 스레드2가 findById()로 question2를 변수에 저장한다면 그 시점 이후에 question 변수를 사용할 때 스레드1은 question2를 사용하게 될 것임
 * 따라서 execute 메서드의 로컬 변수로 구현함으로써 해결해야함
 */

public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}
