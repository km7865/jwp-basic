package next.service;

import next.CannotDeleteException;
import next.repository.AnswerRepository;
import next.repository.QuestionRepository;
import next.model.Question;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QnaServiceTest {
    @Mock
    QuestionRepository questionRepository;
    @Mock
    AnswerRepository answerRepository;

    QnaService qnaService;

    @BeforeEach
    public void setup() {
        qnaService = new QnaService(questionRepository, answerRepository);
    }

    @Test
    public void deleteQuestion_없는질문() {
        when(questionRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(CannotDeleteException.class, () -> {
            qnaService.deleteQuestion(1L, new User("asdf", "asdf", "asdf", "asdf"));
        });

        String expectedMessage = "존재하지 않는 질문입니다.";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
   }

   @Test
    public void deleteQuestion() {
       Question question = new Question(1L, "writer", "title", "contents", new Date(), 0);
       questionRepository.insert(question);
       when(questionRepository.findById(1L)).thenReturn(question);
       //Mockito.lenient().when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());


       Exception exception = assertThrows(CannotDeleteException.class, () -> {
           qnaService.deleteQuestion(1L, new User("javajigi", "asdf", "adsf", "asd@ASd"));
               });

       String expectedMessage = "다른 사용자가 쓴 글을 삭제할 수 없습니다.";
       String actualMessage = exception.getMessage();

       assertThat(actualMessage).isEqualTo(expectedMessage);
   }
}
