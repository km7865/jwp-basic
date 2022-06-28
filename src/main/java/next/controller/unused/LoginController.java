package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import next.repository.UserRepository;
import next.model.User;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class LoginController extends AbstractController {
    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }
}
