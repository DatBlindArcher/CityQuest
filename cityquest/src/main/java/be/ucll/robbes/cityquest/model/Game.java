package be.ucll.robbes.cityquest.model;

import be.ucll.robbes.cityquest.infrastructure.repository.CoordinatesConverter;
import be.ucll.robbes.cityquest.infrastructure.repository.QuestionConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private String location;
    @Convert(converter = CoordinatesConverter.class)
    private Coordinates coordinates;
    @Convert(converter = QuestionConverter.class)
    private List<Question> questions;

    public Game(){
        ///default empty constructor
    }

    public Game(String name, String description, String location, Coordinates coordinates, List<Question> questions) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.coordinates = coordinates;
        this.questions = questions;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class GameBuilder {
        private String name;
        private String description;
        private String location;
        private Coordinates coordinates;
        private List<Question> questions;

        private GameBuilder() {
            this.name = "";
            this.description = "";
            this.location = "Default";
            this.coordinates = new Coordinates(0, 0);
            this.questions = new ArrayList<Question>();
        }

        public static GameBuilder NewGame()
        {
            return new GameBuilder();
        }

        public GameBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public GameBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public GameBuilder withLocation(String location, double lat, double lon) {
            this.location = location;
            this.coordinates = new Coordinates(lat, lon);
            return this;
        }

        public GameBuilder withQuestion(String question, double lat, double lon, List<String> answers, int correctAnswer, String extraInformation) {
            this.questions.add(new Question(question, new Coordinates(lat, lon), answers, correctAnswer, extraInformation));
            return this;
        }

        public Game Build()
        {
            Game game = new Game(name, description, location, coordinates, questions);
            return game;
        }
    }
}
