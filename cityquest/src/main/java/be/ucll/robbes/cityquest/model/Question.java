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

    public static class QuestionBuilder {
        private long id;
        private String question;
        private Coordinates coordinates;

        /*
        private QuestionBuilder(long id, String question, Coordinates coordinates) {
            this.id = id;
            this.question = question;
            this.coordinates = coordinates;
        }*/

        private QuestionBuilder() {
            this.id = 0;
            this.question = "Wat is de naam van de sint-pieterskerk?";
            this.coordinates = new Coordinates(0, 0);
        }

        public static QuestionBuilder NewGame()
        {
            return new QuestionBuilder();
        }

        public QuestionBuilder withId(long id) {
            this.id = id;
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
            Question aQuestion = new Question(id, question, coordinates);
            return aQuestion;
        }
    }
}
