package next.controller.user;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.NewAbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import next.controller.UserSessionUtils;
import next.repository.UserRepository;
import next.model.User;

import java.sql.SQLException;

@Slf4j
@Controller(value = "")
public class UserController extends NewAbstractController {
    private UserRepository userRepository;

    @Inject
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userRepository.findAll());
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        String userId = req.getParameter("userId");
        ModelAndView mav = jspView("/user/profile.jsp");
        mav.addObject("user", userRepository.findByUserId(userId));
        return mav;
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return jspView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView form(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return jspView("/user/form.jsp");
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        User user = new User(req.getParameter("userId"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("email"));
        log.debug("User : {}", user);
        userRepository.insert(user);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        User user = userRepository.findByUserId(req.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        ModelAndView mav = jspView("/user/updateForm.jsp");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        User user = userRepository.findByUserId(req.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return jspView("redirect:/");
    }
}
