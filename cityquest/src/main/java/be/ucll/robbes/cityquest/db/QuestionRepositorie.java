package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface QuestionRepositorie extends CrudRepository<Question, UUID> {
}
