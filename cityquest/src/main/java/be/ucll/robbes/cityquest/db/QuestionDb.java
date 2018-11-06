package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Question;

import java.util.List;

public interface QuestionDb {
    public List<Question> getQuestions();
    public Question getQuestion(long id);
    public void addQuestion(Question question);
    public void removeQuestion(long id);
    public void updateQuestion(Question question);
}
