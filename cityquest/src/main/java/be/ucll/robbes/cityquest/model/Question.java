package be.ucll.robbes.cityquest.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private Coordinates coordinates;
    private List<String> answers;
    private int correctAnswer;
    private String extraInformation;

    public Question() {}

    public Question(String question, Coordinates coordinates, List<String> answers, int correctAnswer, String extraInformation) {
        this.question = question;
        this.coordinates = coordinates;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.extraInformation = extraInformation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public static class QuestionBuilder {

        private Coordinates coordinates;
        private String question;
        private List<String> answers = new ArrayList<>();
        private int correctAnswer;
        private String extraInfo;

        public static QuestionBuilder aQuestion() {
            return new QuestionBuilder();
        }

        public QuestionBuilder withCoordinates(double lat, double lon) {
            return this.withCoordinates(new Coordinates(lat, lon));
        }

        public QuestionBuilder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates; return this;
        }

        public QuestionBuilder withQuestion(String question) {
            this.question = question; return this;
        }

        public QuestionBuilder withAnswers(List<String> answers) {
            this.answers = answers; return this;
        }

        public QuestionBuilder withAnswer(String answer) {
            this.answers.add(answer); return this;
        }

        public QuestionBuilder withCorrectAnswer(int correctAnswer) {
            this.correctAnswer = correctAnswer; return this;
        }

        public QuestionBuilder withExtraInformation(String extraInfo) {
            this.extraInfo = extraInfo; return this;
        }

        public Question build() {
            Question question = new Question();
            question.coordinates = this.coordinates;
            question.question = this.question;
            question.answers = this.answers;
            question.correctAnswer = this.correctAnswer;
            question.extraInformation = this.extraInfo;
            return question;
        }
    }
}
