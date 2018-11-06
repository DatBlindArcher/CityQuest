package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDbInMemory implements QuestionDb{
    private Map<Long,Question> questionList;

    public QuestionDbInMemory() {
        questionList = new HashMap<>();
    }

    

    public Map<Long, Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Map<Long, Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public List<Question> getQuestions() {
        return null;
    }

    @Override
    public Question getQuestion(long id) {
        return null;
    }

    @Override
    public void addQuestion(Question question) {

    }

    @Override
    public void removeQuestion(long id) {

    }

    @Override
    public void updateQuestion(Question question) {

    }
}
