package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyController {
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public ModelAndView test(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("test method");

        

        return null;
    }
}
