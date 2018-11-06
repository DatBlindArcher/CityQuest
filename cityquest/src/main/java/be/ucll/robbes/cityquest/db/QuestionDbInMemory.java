package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Question;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDbInMemory implements QuestionDb{
    private Map<Long,Question> questionMap;

    public QuestionDbInMemory() {
        questionMap = new HashMap<>();
    }

    public QuestionDbInMemory(Map<Long, Question> questionMap) {
        setQuestionMap(questionMap);
    }

    public Map<Long, Question> getQuestionMap() {
        return questionMap;
    }

    public void setQuestionMap(Map<Long, Question> questionMap) {
        this.questionMap = questionMap;
    }

    @Override
    public Collection<Question> getQuestions() {
        return questionMap.values();
    }

    @Override
    public Question getQuestion(long id) {
        return questionMap.get(id);
    }

    @Override
    public void addQuestion(Question question) {
        questionMap.put(question.getId(), question);
    }

    @Override
    public void removeQuestion(long id) {
        questionMap.remove(id);
    }

    @Override
    public void updateQuestion(Question question) {
        questionMap.remove(question.getId());
        questionMap.put(question.getId(), question);
    }
}
