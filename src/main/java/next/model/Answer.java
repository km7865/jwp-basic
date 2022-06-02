package next.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
public class Answer {
    Long answerId;
    String writer;
    String contents;
    LocalDateTime createdDate;
    Long questionId;

    public Answer(Long answerId, String writer, String contents, LocalDateTime createdDate, Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Answer(Long answerId, String writer, String contents, Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.questionId = questionId;
    }
}
