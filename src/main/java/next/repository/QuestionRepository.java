package next.repository;

import next.model.Question;

import java.util.List;

public interface QuestionRepository {
    public abstract Question insert(Question question);
    public abstract List<Question> findAll();
    public abstract Question findById(long questionId);
    public abstract void update(Question question);
    public abstract void delete(long questionId);
    public abstract void updateCountOfAnswer(long questionId);
}
