package next.repository;

import core.annotation.Repository;
import next.model.Answer;

import java.util.List;

public interface AnswerRepository {
    public abstract Answer insert(Answer answer);
    public abstract Answer findById(long answerId);
    public abstract List<Answer> findAllByQuestionId(long questionId);
    public abstract void delete(Long answerId);
}
