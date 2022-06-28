package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.repository.UserRepository;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ListUserController extends AbstractController {
    private UserRepository userRepository;

    public ListUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userRepository.findAll());
        return mav;
    }
}
