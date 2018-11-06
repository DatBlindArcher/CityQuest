package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

    @GetMapping
    public ResponseEntity<Game[]> findAll() {
        return ResponseEntity.ok(new Game[]{ GameBuilder.NewGame().withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2).withQuestion("What is the name of this place?", 5.5, 6.6).Build() });
    }
}