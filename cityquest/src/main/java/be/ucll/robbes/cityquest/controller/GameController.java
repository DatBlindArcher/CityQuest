package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Answer;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;

    @Autowired
    public GameController(GameRepository repository)
    {
        this.repository = repository;

        //Some inputs
        List<String> answers = new ArrayList<String>();
        answers.add("juist");
        answers.add("false");

        Game game = GameBuilder.NewGame()
                .withName("LeuvenSpel").withDescription("Descriptie van LeuveSpel.").withLocation("Leuven", 1.1, 2.2)
                .withQuestion("Hoe groot is de Sint-pieters kerk?", 5.5, 6.6, answers, 0, "Het is juist").Build();

        this.repository.save(game);
    }

    @GetMapping
    public ResponseEntity<Iterable<Game>> findAllGames() {
        Iterable<Game> games = repository.findAll();

        for (Game game : games)
        {
            game.setQuestions(null);
        }

        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable UUID id) {
        Optional<Game> op = repository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Game> postGame(@RequestBody Game game) {
        Game result = repository.save(game);
        return ResponseEntity.ok(result);
    }
}