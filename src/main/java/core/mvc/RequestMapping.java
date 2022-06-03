package core.mvc;

import java.util.HashMap;
import java.util.Map;

import next.controller.*;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * /questions/show/{questionId} 와 같은 형태의 요청을 어떻게 받을지? GET 요청 URL show?questionId=# 와 같은 형식으로
 *
 */
public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

        mappings.put("/questions/create", new CreateQuestionController());
        mappings.put("/questions/form", new QuestionFormController());
        mappings.put("/questions/show", new QuestionShowController());

        mappings.put("/answers/delete", new AnswerController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
