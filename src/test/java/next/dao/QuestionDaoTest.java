package next.dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import next.model.Question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionDaoTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionDaoTest.class);

    @Test
    public void crud() {
        //Question question = mock(Question.class);
        //log.debug("mocked quesiton: {}", question);

        Question question = new Question("writer", "title", "contents");
        QuestionDao questionDao = QuestionDao.getInstance();
        Question savedQuestion = questionDao.insert(question);

        log.debug("question : {}", savedQuestion);
        assertEquals(question, savedQuestion);
        assertThat("").isEqualTo("");
    }

}
