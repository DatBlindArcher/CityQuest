package be.ucll.robbes.cityquest.model;

public class Question {
    private long id;
    private String question;
    private Coordinates coordinates;

    public Question(String question, Coordinates coordinates) {
        setId(0);
        setQuestion(question);
        setCoordinates(coordinates);
    }

    public Question(long id, String question, Coordinates coordinates) {
        setId(id);
        setQuestion(question);
        setCoordinates(coordinates);
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
