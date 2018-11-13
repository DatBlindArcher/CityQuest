package be.ucll.robbes.cityquest.model;

public class Answer {
    private String text;
    private boolean correct;
    private String description;

    public Answer() {
        //why comment: for deserialization
    }

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public Answer(String text, boolean correct, String description) {
        this.text = text;
        this.correct = correct;
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
