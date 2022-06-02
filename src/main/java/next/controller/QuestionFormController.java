package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * /questions/form
 *
 */
public class QuestionFormController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (UserSessionUtils.isLogined(req.getSession())) {
            return "/qna/form.jsp";
        } else {
            return "redirect:/";
        }
    }
}
