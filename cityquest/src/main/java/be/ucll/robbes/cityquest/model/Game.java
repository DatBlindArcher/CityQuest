package be.ucll.robbes.cityquest.model;

import java.util.List;

public class Game {
    private String name;
    private City city;
    private List<Question> questions;

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
}
