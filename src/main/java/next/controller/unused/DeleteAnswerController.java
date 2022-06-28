package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.repository.AnswerRepository;
import next.model.Result;
import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class DeleteAnswerController extends AbstractController {
    private AnswerRepository answerRepository;

    public DeleteAnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long answerId = Long.parseLong(request.getParameter("answerId"));

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
