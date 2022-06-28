package next.controller;

import core.nmvc.BeanScanner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class ControllerScannerTest {
    private BeanScanner bs;

    @BeforeAll
    public void setup() {
        bs = new BeanScanner("next");
    }

    @Test
    public void test() {
        Map<Class<?>, Object> controllers = bs.getBeans();
        Object obj = controllers.get(MyController.class);
        if (obj instanceof MyController) {
            log.debug("done!");
        }
    }
}
