package be.ucll.robbes.cityquest.controller;

import be.ucll.robbes.cityquest.db.GameRepository;
import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Game.GameBuilder;
import be.ucll.robbes.cityquest.model.GameInfo;
import be.ucll.robbes.cityquest.model.Leaderboard;
import be.ucll.robbes.cityquest.model.Question;
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
            URI service = leaderboardServiceUrl()
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

    @PostMapping("/{id}/entry")
    public ResponseEntity<Leaderboard.Entry> postResult(@PathVariable UUID id, @RequestBody Leaderboard.Entry leaderboardEntry) throws ServiceUnavailableException {
        Optional<Game> op = repository.findById(id);
        if (op.isPresent())
        {
            URI service = leaderboardServiceUrl()
                    .map(s -> s.resolve("/leaderboard"))
                    .orElseThrow(ServiceUnavailableException::new);

            return restTemplate.postForEntity(service, leaderboardEntry, Leaderboard.Entry.class);
            //return restTemplate.getForEntity(service, Leaderboard.Entry[].class);
        }
        else return ResponseEntity.notFound().build();
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

    private Optional<URI> leaderboardServiceUrl() {
        return discoveryClient.getInstances("leaderboard")
                .stream()
                .map(ServiceInstance::getUri)
                .findFirst();
    }

    @Autowired
    public GameController(GameRepository repository){
        this.repository = repository;
        setDefaultGame();
    }

    private void setDefaultGame() {
        if (repository == null){
            throw new IllegalArgumentException("GEEN REPO in GameController");
        }
        Question question1 = Question.QuestionBuilder.aQuestion()
                .withQuestion("Hoe oud is het historisch stadhuis van Leuven?")
                .withAnswer("1469").withAnswer("1439").withAnswer("2018")
                .withCorrectAnswer(0)
                .withExtraInformation("Eerste steen was in 1439, geopend 1469")
                .withCoordinates(50.8789962,4.6994371)
                .build();
        Question question2 = Question.QuestionBuilder.aQuestion()
                .withQuestion("Hoeveel perrons zijn er in Leuven station?")
                .withAnswer("6").withAnswer("9").withAnswer("14")
                .withCorrectAnswer(2)
                .withExtraInformation("A, B, C, D + perron 13")
                .withCoordinates(50.880833, 4.716111)
                .build();
        Question question3 = Question.QuestionBuilder.aQuestion()
                .withQuestion("Hoeveel 'poorten' zijn er op de ring van Leuven?")
                .withAnswer("7").withAnswer("9").withAnswer("11").withAnswer("13")
                .withCorrectAnswer(2)
                .withCoordinates(50.8774469,4.6859718)
                .build();

        Game game = GameBuilder.aGame()
                .withName("Het grote leuvense stadspel")
                .withCity("Leuven")
                .withCoordinates(50.8789962, 4.6994371)
                .withDescription("This is the default game, brought to you by De Robbes")
                .withQuestion(question1)
                .withQuestion(question2)
                .withQuestion(question3)
                .build();

        repository.save(game);
    }
}