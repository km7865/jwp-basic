package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void insert() {
        Question expected = new Question(9L, "writer1", "title1", "contents1",
                LocalDateTime.now(), 0);

        QuestionDao dao = new QuestionDao();
        dao.insert(expected);
        Question actual = dao.findByQuestionId(expected.getQuestionId());

        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getContents(), actual.getContents());
        assertEquals(expected.getCountOfAnswer(), actual.getCountOfAnswer());
    }

    @Test
    public void update() {
        QuestionDao dao = new QuestionDao();
        Question previous = dao.findByQuestionId(1L);
        Question expected = new Question(previous.getQuestionId(),
                previous.getWriter(),
                "modified title",
                "modified contents",
                previous.getCreatedDate(),
                previous.getCountOfAnswer());

        dao.update(expected);
        Question actual = dao.findByQuestionId(expected.getQuestionId());

        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getContents(), actual.getContents());
        assertEquals(expected.getCountOfAnswer(), actual.getCountOfAnswer());
    }

    @Test
    public void findAll() {
        QuestionDao dao = new QuestionDao();
        int expectedSize = 8;

        List<Question> questions = dao.findAll();
        assertEquals(expectedSize, questions.size());
    }

    @Test
    public void findByUserId() {
        QuestionDao dao = new QuestionDao();

        Question expected = new Question(5L, "johnburr",
                "이클립스 JRE설정에 대해서 질문을 드립니다.",
                "이곳에서 보면 이클립스의 jre에 대해서 3개의 설정이 나옵니다. jre 버전 설정 실행환경 설정(execution environment) 컴파일러 설정");

        Question actual = dao.findByQuestionId(expected.getQuestionId());

        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getContents(), actual.getContents());
    }
}
