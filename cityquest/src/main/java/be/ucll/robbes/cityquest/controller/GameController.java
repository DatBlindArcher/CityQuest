package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;

    @Autowired
    public GameController(GameRepository repository)
    {
        this.repository = repository;

        /*/Some inputs
        List<String> answers = new ArrayList<String>();
        answers.add("juist");
        answers.add("false");

        Game game = GameBuilder.aGame()
                .withName("LeuvenSpel").withDescription("Descriptie van LeuveSpel.").withLocation("Leuven", 1.1, 2.2)
                .withQuestion("Hoe groot is de Sint-pieters kerk?", 5.5, 6.6, answers, 0, "Het is juist").build();

        this.repository.save(game);
        //*/
    }

    @GetMapping
    public ResponseEntity<List<Game>> findAllGames() {
        return ResponseEntity.ok(StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(game -> {game.setQuestions(null); return game;})
                .collect(Collectors.toList()));
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

    // Why: For some reason the global Cors setting does not work for PUT
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Game> putGame(@PathVariable UUID id, @RequestBody Game game) {
        repository.deleteById(id);
        Game result = repository.save(game);
        return ResponseEntity.ok(result);
    }
}