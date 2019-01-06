package be.ucll.robbes.leaderboard.controller;

import be.ucll.robbes.leaderboard.db.ResultRepository;
import be.ucll.robbes.leaderboard.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
    private ResultRepository repository;

    @Autowired
    public LeaderboardController(ResultRepository repository){
        this.repository=repository;
        //Some inputs
        /*UUID game1Id = UUID.fromString("8c0066b8-58b3-437b-a04c-12a839da4017");
        Result test1 = new Result(100, "Robbe", UUID.randomUUID());
        this.repository.save(test1);
        Result test2 = new Result(200, "Robbe", game1Id);
        this.repository.save(test2);
        Result test3 = new Result(300, "Robbe", game1Id);
        this.repository.save(test3);
        Result test4 = new Result(400, "Robbe", UUID.randomUUID());
        this.repository.save(test4);
        //*/
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        return ResponseEntity.ok(StreamSupport.stream(repository.findAll().spliterator(), false)
                //.map(result -> {return result;})
                .collect(Collectors.toList()));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<Result>> getGameResults(@PathVariable UUID gameId){
        List<Result> gameResults = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(s -> s.getGameId().equals(gameId))
                .collect(Collectors.toList());

        Collections.sort(gameResults, new Result());
		
		List<Result> result = Lists.reverse(gameResults);

        return ResponseEntity.ok(result);
    }

    /*@GetMapping("/{amount}")
    public ResponseEntity<List<Result>> getTopResults(@PathVariable int amount){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResult (@PathVariable UUID id){
        return null;
    }*/

    @PostMapping
    @Transactional
    public ResponseEntity<Result> postResult(@RequestBody Result result) {
        Result res = repository.save(result);
        return ResponseEntity.ok(res);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Result> putResult (@PathVariable UUID id, @RequestBody Result result){
        if (repository.existsById(id) && id.equals(result.getId())) {
            Result res = repository.save(result);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }*/
}
