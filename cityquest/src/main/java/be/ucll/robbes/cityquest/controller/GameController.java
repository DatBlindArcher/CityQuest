package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import be.ucll.robbes.cityquest.model.GameInfo;
import be.ucll.robbes.cityquest.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.naming.ServiceUnavailableException;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// Why: For some reason the global Cors setting does not work for PUT
@CrossOrigin
@RestController
@RequestMapping("/games")
public class GameController {

    @Inject
    private DiscoveryClient discoveryClient;

    @Inject
    private GameRepository repository;

    @Inject
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<List<Game>> findAllGames() {
        return ResponseEntity.ok(StreamSupport.stream(repository.findAll().spliterator(), false)
                .peek(game -> game.setQuestions(null))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameInfo> getGame(@PathVariable UUID id) throws RestClientException, ServiceUnavailableException {
        Optional<Game> op = repository.findById(id);

        if (op.isPresent())
        {
            URI service = recommendationServiceUrl()
                    .map(s -> s.resolve("/leaderboard/" + id))
                    .orElseThrow(ServiceUnavailableException::new);

            Leaderboard leaderboard = new Leaderboard(restTemplate
                    .getForEntity(service, Leaderboard.Entry[].class)
                    .getBody());

            return ResponseEntity.ok(new GameInfo(op.get(), leaderboard));
        }

        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Game> postGame(@RequestBody Game game) {
        Game result = repository.save(game);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> putGame(@PathVariable UUID id, @RequestBody Game game) {
        if (repository.existsById(id))
            if (id.equals(game.getId())) {
            Game result = repository.save(game);
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.notFound().build();
    }

    private Optional<URI> recommendationServiceUrl() {
        return discoveryClient.getInstances("leaderboard")
                .stream()
                .map(ServiceInstance::getUri)
                .findFirst();
    }
}