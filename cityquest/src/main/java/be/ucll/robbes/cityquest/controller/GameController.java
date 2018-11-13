package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    @GetMapping
    public ResponseEntity<Game[]> findAllGames() {
        Game[] games = new Game[]{ GameBuilder.NewGame()
                .withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2)
                .withQuestion("What is the name of this place?", 5.5, 6.6).Build() };

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
                .withQuestion("What is the name of this place?", 5.5, 6.6).Build();

        return ResponseEntity.ok(game);
    }
}