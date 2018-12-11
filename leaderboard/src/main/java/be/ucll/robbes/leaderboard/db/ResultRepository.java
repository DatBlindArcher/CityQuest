package be.ucll.robbes.leaderboard.db;

import be.ucll.robbes.leaderboard.model.Result;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ResultRepository extends CrudRepository<Result, UUID>{
}
