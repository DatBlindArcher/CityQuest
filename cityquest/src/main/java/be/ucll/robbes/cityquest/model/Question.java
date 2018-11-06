package be.ucll.robbes.cityquest.model;

public class Question {
    private String question;
    private Coordinates coordinates;

    public Question(String question, Coordinates coordinates) {
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


    public static class QuestionBuilder {
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
            Question aQuestion = new Question(question, coordinates);
            return aQuestion;
        }
    }
}
