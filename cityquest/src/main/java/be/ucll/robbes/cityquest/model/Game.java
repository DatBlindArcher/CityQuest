package be.ucll.robbes.cityquest.model;

import be.ucll.robbes.cityquest.infrastructure.repository.JpaJsonConverter;

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
    @Convert(converter = JpaJsonConverter.CityConverter.class)
    private City city;
    @Convert(converter = JpaJsonConverter.QuestionConverter.class)
    private List<Question> questions;

    public Game(){
        ///default empty constructor
    }

    public Game(String name, City city, List<Question> questions) {
        this.name = name;
        this.city = city;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class GameBuilder {
        private String name;
        private City city;
        private List<Question> questions;

        private GameBuilder() {
            this.name = "";
            this.city = new City("Default", new Coordinates(0, 0));
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

        public GameBuilder withCity(String name, double lat, double lon) {
            this.city = new City(name, new Coordinates(lat, lon));
            return this;
        }

        public GameBuilder withQuestion(String question, double lat, double lon) {
            this.questions.add(new Question(question, new Coordinates(lat, lon)));
            return this;
        }

        public Game Build()
        {
            Game game = new Game(name, city, questions);
            return game;
        }
    }
}
