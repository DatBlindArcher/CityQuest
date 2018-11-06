package be.ucll.robbes.cityquest.model;

public class Question {
    private String question;
    private Coordinates coordinates;

    public Question(String question, Coordinates coordinates) {
        this.question = question;
        this.coordinates = coordinates;
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
}
