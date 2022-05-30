package next.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import core.db.DataBase;
import jakarta.servlet.http.HttpSession;
import next.model.User;

// @WebServlet(value = { "/users/login", "/users/loginForm" })
public class LoginController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/index.jsp";
        } else {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        forward("/user/login.jsp", req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String userId = req.getParameter("userId");
//        String password = req.getParameter("password");
//        User user = DataBase.findUserById(userId);
//        if (user == null) {
//            req.setAttribute("loginFailed", true);
//            forward("/user/login.jsp", req, resp);
//            return;
//        }
//
//        if (user.matchPassword(password)) {
//            HttpSession session = req.getSession();
//            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
//            resp.sendRedirect("/");
//        } else {
//            req.setAttribute("loginFailed", true);
//            forward("/user/login.jsp", req, resp);
//        }
//    }
//
//    private void forward(String forwardUrl, HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
//        rd.forward(req, resp);
//    }
}
