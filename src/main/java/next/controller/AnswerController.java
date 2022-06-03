package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;

/*
 * /answers/delete?answerId={answerId}
 * 답변이 삭제되면 삭제된 영역만 처리하면 되지만 서버에 요청을 보내 css, js 등 모든 정적 요소를 재요청하게 된다.
 * 이 문제를 해결하기 위해 AJAX 가 등장했다.
 */
public class AnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        new AnswerDao().remove(Long.parseLong(req.getParameter("answerId")));

        return "redirect:/";
    }
}
