package next.repository;

import core.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {
    @BeforeAll
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserRepository userRepository = new UserRepository(new JdbcTemplate());
        userRepository.insert(expected);

        User actual = userRepository.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

}
