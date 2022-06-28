package next.controller.unused;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import next.repository.UserRepository;
import next.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private UserRepository userRepository;

    public CreateUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        log.debug("User : {}", user);
        userRepository.insert(user);
        return jspView("redirect:/");
    }
}
