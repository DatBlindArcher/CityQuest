package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
        Game game = GameBuilder.NewGame()
                .withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2)
                .withQuestion("Hoe groot is de Sint-pieters kerk?", 5.5, 6.6).Build();

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

    @GetMapping("/quiz")
    public ResponseEntity<Game> getGame(@RequestParam UUID id) {
        Optional<Game> op = repository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Game> postGame(@RequestBody Game game) {
        Game result = repository.save(game);
        return ResponseEntity.ok(result);
    }
}