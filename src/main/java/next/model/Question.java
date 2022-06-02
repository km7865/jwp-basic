package next.model;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Question {
    private Long questionId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private int countOfAnswer;

    public Question(Long questionId, String writer, String title, String contents, LocalDateTime createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(Long questionId, String writer, String title, String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Question(String writer, String title, String contents, LocalDateTime createdDate) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Question other = (Question) obj;

        if (questionId != other.getQuestionId()) return false;
        if (writer != other.getWriter()) return false;
        if (title != other.getTitle()) return false;
        if (contents != other.getContents()) return false;

        return true;
    }
}
