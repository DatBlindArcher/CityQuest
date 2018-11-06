package be.ucll.robbes.cityquest.db;

import be.ucll.robbes.cityquest.model.Game;
import be.ucll.robbes.cityquest.model.Question;

public class QuestionDbInMemoryStub {
    QuestionDb db = new QuestionDbInMemory();

    //Question question1 = new Question[]{ Game.GameBuilder.NewGame().withName("LeuvenSpel").withCity("Leuven", 1.1, 2.2).withQuestion("What is the name of this place?", 5.5, 6.6).Build() }
}
