package next.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import core.db.DataBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@WebServlet("")
public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("HomeController.execute()");
        req.setAttribute("users", DataBase.findAll());
        return "/index.jsp";
    }

    private static final long serialVersionUID = 1L;

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("users", DataBase.findAll());
//        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
//        rd.forward(req, resp);
//    }
}
