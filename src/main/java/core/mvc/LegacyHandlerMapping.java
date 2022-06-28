package core.mvc;

import java.util.HashMap;
import java.util.Map;

import core.HandlerMapping;
import core.jdbc.JdbcTemplate;
import jakarta.servlet.http.HttpServletRequest;
import next.repository.JdbcQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.HomeController;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        // mappings.put("/", new HomeController(new JdbcQuestionRepository(new JdbcTemplate())));
//        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
//        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
//        mappings.put("/users", new ListUserController());
//        mappings.put("/users/login", new LoginController());
//        mappings.put("/users/profile", new ProfileController());
//        mappings.put("/users/logout", new LogoutController());
//        mappings.put("/users/create", new CreateUserController());
//        mappings.put("/users/updateForm", new UpdateFormUserController());
//        mappings.put("/users/update", new UpdateUserController());

//        mappings.put("/qna/show", new ShowQuestionController());
//        mappings.put("/qna/form", new CreateFormQuestionController());
//        mappings.put("/qna/create", new CreateQuestionController());
//        mappings.put("/qna/updateForm", new UpdateFormQuestionController());
//        mappings.put("/qna/update", new UpdateQuestionController());
//        mappings.put("/qna/delete", new DeleteQuestionController());
//
//        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController());
//        mappings.put("/api/qna/list", new ApiListQuestionController());
//        mappings.put("/api/qna/addAnswer", new AddAnswerController());
//        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        logger.info("Initialized Request Mapping!");
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Controller getHandler(HttpServletRequest req) {
        return mappings.get(req.getRequestURI());
    }
}
