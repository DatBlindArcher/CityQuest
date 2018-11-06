package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameRepositorie extends CrudRepository<Game, UUID> {
}
