package be.ucll.robbes.cityquest.model;

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

    /*ublic static class QuestionBuilder {
        private String question;
        private Coordinates coordinates;

        private QuestionBuilder() {
            this.question = "Wat is de naam van de sint-pieterskerk?";
            this.coordinates = new Coordinates(0, 0);
        }

        public static QuestionBuilder NewGame()
        {
            return new QuestionBuilder();
        }

        public QuestionBuilder withId(long id) {
            return this;
        }

        public QuestionBuilder withQuestion(String question) {
            this.question = question;
            return this;
        }

        public QuestionBuilder withCoordinates(double lat, double lon) {
            this.coordinates = new Coordinates(lat, lon);
            return this;
        }

        public Question Build()
        {
            Question aQuestion = new Question(question, coordinates, );
            return aQuestion;
        }
    }*/
}
