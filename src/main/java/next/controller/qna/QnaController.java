package next.controller.qna;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.jdbc.DataAccessException;
import core.mvc.ModelAndView;
import core.mvc.NewAbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.repository.AnswerRepository;
import next.repository.QuestionRepository;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

import java.util.List;

@Slf4j
@Controller
public class QnaController extends NewAbstractController {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private QnaService qnaService;

    @Inject
    public QnaController(QuestionRepository questionRepository, AnswerRepository answerRepository, QnaService qnaService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.qnaService = qnaService;
    }

    @RequestMapping(value = "/qna/show", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionRepository.findById(questionId);
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }

    @RequestMapping(value = "/qna/form", method = RequestMethod.GET)
    public ModelAndView form(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        return jspView("/qna/form.jsp");
    }

    @RequestMapping(value = "/qna/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Question question = new Question(user.getUserId(), req.getParameter("title"),
                req.getParameter("contents"));
        questionRepository.insert(question);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/qna/updateForm", method = RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionRepository.findById(questionId);
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }

    @RequestMapping(value = "/qna/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionRepository.findById(questionId);
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question newQuestion = new Question(question.getWriter(), req.getParameter("title"),
                req.getParameter("contents"));
        question.update(newQuestion);
        questionRepository.update(question);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/qna/delete", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                    .addObject("answers", qnaService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }

    @RequestMapping(value = "/api/qna/deleteQuestion", method = RequestMethod.POST)
    public ModelAndView apiDeleteQuestion(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }

    @RequestMapping(value = "/api/qna/list", method = RequestMethod.GET)
    public ModelAndView apiList(HttpServletRequest req, HttpServletResponse resp) {
        return jsonView().addObject("questions", questionRepository.findAll());
    }

    @RequestMapping(value = "/api/qna/addAnswer", method = RequestMethod.POST)
    public ModelAndView addAnswer(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Answer answer = new Answer(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerRepository.insert(answer);
        questionRepository.updateCountOfAnswer(savedAnswer.getQuestionId());

        return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
    }

    @RequestMapping(value = "/api/qna/deleteAnswer", method = RequestMethod.POST)
    public ModelAndView deleteAnswer(HttpServletRequest req, HttpServletResponse resp) {
        Long answerId = Long.parseLong(req.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            answerRepository.delete(answerId);
            mav.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}
