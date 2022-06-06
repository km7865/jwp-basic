package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.AnswerDao;
import next.model.Result;
import next.view.ModelAndView;

@Slf4j
public class DeleteAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        AnswerDao dao = new AnswerDao();
        log.debug(req.getParameter("answerId"));
        dao.remove(Long.parseLong(req.getParameter("answerId")));
        Result result = new Result(true, "OK");
        return jsonView().addObject("result", result);
    }
}
