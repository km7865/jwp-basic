package next.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private Map<String, Controller> controllers = new HashMap<>();

    void initMapping() {
        controllers.put("/", new HomeController());
        controllers.put("/users/create", new CreateUserController());
        controllers.put("/users/form", new ForwardController("/user/form.jsp"));
        controllers.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        controllers.put("/users", new ListUserController());
        controllers.put("/users/login", new LoginController());
        controllers.put("/users/logout", new LogoutController());
        controllers.put("/users/profile", new ProfileController());
        controllers.put("/users/update", new UpdateUserController());
        controllers.put("/users/updateForm", new UpdateUserController());
    }

    public Controller getController(String path) {
        return controllers.get(path);
    }

    public void put(String url, Controller controller) {
        controllers.put(url, controller);
    }
}
