package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnswerDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        AnswerDao dao = new AnswerDao();
        Answer expected = new Answer(6L, "writer1", "contents1", 1L);

        dao.insert(expected);
        List<Answer> actual = dao.findByQuestionId(expected.getQuestionId());

        assertEquals(expected.getWriter(), actual.get(0).getWriter());
        assertEquals(expected.getContents(), actual.get(0).getContents());
        assertEquals(expected.getQuestionId(), actual.get(0).getQuestionId());
    }

    @Test
    public void findAll() {
        int expectedSize = 5;
        List<Answer> answers = new AnswerDao().findAll();

        for (Answer a : answers) System.out.println(a.getAnswerId());
        assertEquals(expectedSize, answers.size());
    }
}
