package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepositorie extends CrudRepository<Game, UUID> {
    /*public List<Game> getGames();
    public Game getGame(UUID id);
    public void addGame(Game game);
    public void removeGame(UUID id);
    public void updateGame(UUID id, Game game);*/
}
