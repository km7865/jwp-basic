package next.controller;

import core.nmvc.ControllerScanner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class ControllerScannerTest {
    private ControllerScanner cs;

    @BeforeAll
    public void setup() {
        cs = new ControllerScanner("next.controller");
    }

    @Test
    public void test() {
        Map<Class<?>, Object> controllers = cs.getControllers();
        Object obj = controllers.get(MyController.class);
        if (obj instanceof MyController) {
            log.debug("done!");
        }
    }
}
