package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.repository.UserRepository;
import next.model.User;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class UpdateFormUserController extends AbstractController {
    private UserRepository userRepository;

    public UpdateFormUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userRepository.findByUserId(request.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        ModelAndView mav = jspView("/user/updateForm.jsp");
        mav.addObject("user", user);
        return mav;
    }
}
