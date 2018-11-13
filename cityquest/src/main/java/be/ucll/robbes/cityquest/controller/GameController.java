package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Answer;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import be.ucll.robbes.cityquest.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;

    @Autowired
    public GameController(GameRepository repository)
    {
        this.repository = repository;

        //Some inputs
        List<Answer> answers = new ArrayList<Answer>(){};
        answers.add(new Answer("juist",true));
        answers.add(new Answer("fout;",false));

        Game game = GameBuilder.NewGame()
                .withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2)
                .withQuestion("Hoe groot is de Sint-pieters kerk?", 5.5, 6.6, answers).Build();

        this.repository.save(game);
    }

    @GetMapping
    public ResponseEntity<Iterable<Game>> findAllGames() {
        /*Game[] games = new Game[]{ GameBuilder.NewGame()
                .withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2)
                .withQuestion("What is the name of this place?", 5.5, 6.6).Build() };*/

        Iterable<Game> games = repository.findAll();

        for (Game game : games)
        {
            game.setQuestions(null);
        }

        return ResponseEntity.ok(games);
    }

    @GetMapping("/quiz")
    public ResponseEntity<Game> getGame(@RequestParam UUID id) {
        Game game = GameBuilder.NewGame()
                .withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2)
                .withQuestion("What is the name of this place?", 5.5, 6.6, new ArrayList<Answer>()).Build();

        return ResponseEntity.ok(game);
    }
}