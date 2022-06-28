package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.repository.UserRepository;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ProfileController extends AbstractController {
    private UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        ModelAndView mav = jspView("/user/profile.jsp");
        mav.addObject("user", userRepository.findByUserId(userId));
        return mav;
    }
}
