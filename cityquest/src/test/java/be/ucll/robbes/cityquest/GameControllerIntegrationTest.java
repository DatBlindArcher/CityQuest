package be.ucll.robbes.cityquest;

import be.ucll.robbes.cityquest.model.Game;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static be.ucll.robbes.cityquest.model.Game.GameBuilder.aGame;
import static be.ucll.robbes.cityquest.model.Question.QuestionBuilder.aQuestion;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

    public class GameControllerIntegrationTest extends AbstractControllerIntegrationTest {

        @Test
        public void testRetrieveGames_NoSavedGames_EmptyList() throws JSONException {
            String actualGames = httpGet("/games");
            String expectedGames = "[]";

            assertThatJson(actualGames).isEqualTo(expectedGames);
        }

        @Test
        public void testPostGame() throws JSONException {
            Game game = aGame()
                    .withName("Leuven Treasure Hunt")
                    .withCity("Leuven")
                    .withCoordinates(50.8798, 4.7005)
                    .withDescription("Follow the map of questions and find the treasure!")
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879154, 4.704496)
                            .withQuestion("What is the main topic in the M-Museum")
                            .withAnswer("National History")
                            .withAnswer("Fashion")
                            .withAnswer("Modern art")
                            .withCorrectAnswer(3)
                            .withExtraInformation("Art museum opened in 2009, architect-designed to unite old buildings with contemporary architecture.")
                            .build())
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879127, 4.701235)
                            .withQuestion("How many statues are on the walls?")
                            .withAnswer("196")
                            .withAnswer("236")
                            .withAnswer("266")
                            .withCorrectAnswer(2)
                            .withExtraInformation("15th-century, Gothic-style, former municipal headquarters with spires & 236 sculptures on the walls.")
                            .build())
                    .build();

            String actualGameAsJson = httpPost("/games", game);
            String expectedGameAsJson = jsonTestFile("testPostGame.json");

            assertThatJson(actualGameAsJson).isEqualTo(expectedGameAsJson);
        }

        @Test
        public void testPutGame() throws JSONException {
            //TODO
            Game game = aGame()
                    .withName("Leuven Treasure Hunt")
                    .withCity("Leuven")
                    .withCoordinates(50.8798, 4.7005)
                    .withDescription("Follow the map of questions and find the treasure!")
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879154, 4.704496)
                            .withQuestion("What is the main topic in the M-Museum")
                            .withAnswer("National History")
                            .withAnswer("Fashion")
                            .withAnswer("Modern art")
                            .withCorrectAnswer(3)
                            .withExtraInformation("Art museum opened in 2009, architect-designed to unite old buildings with contemporary architecture.")
                            .build())
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879127, 4.701235)
                            .withQuestion("How many statues are on the walls?")
                            .withAnswer("196")
                            .withAnswer("236")
                            .withAnswer("266")
                            .withCorrectAnswer(2)
                            .withExtraInformation("15th-century, Gothic-style, former municipal headquarters with spires & 236 sculptures on the walls.")
                            .build())
                    .build();

            String actualGameAsJson = httpPost("/games", game);
            JSONObject jsonObject = new JSONObject(actualGameAsJson);
            String id = jsonObject.getString("id");

            game.setName("Changed");
            String updatedGameAsJson = httpPut("/games/" + id, game);
            String expectedGameAsJson = jsonTestFile("testPutGame.json");
            assertThatJson(updatedGameAsJson).isEqualTo(expectedGameAsJson);
        }

        @Test
        public void testGetGames_WithSavedGame_ListWithSavedGame() throws JSONException {
            //TODO
            Game game1 = aGame()
                    .withName("Leuven Treasure Hunt1")
                    .withCity("Leuven")
                    .withCoordinates(50.8798, 4.7005)
                    .withDescription("Follow the map of questions and find the treasure!")
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879154, 4.704496)
                            .withQuestion("What is the main topic in the M-Museum")
                            .withAnswer("National History")
                            .withAnswer("Fashion")
                            .withAnswer("Modern art")
                            .withCorrectAnswer(3)
                            .withExtraInformation("Art museum opened in 2009, architect-designed to unite old buildings with contemporary architecture.")
                            .build())
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879127, 4.701235)
                            .withQuestion("How many statues are on the walls?")
                            .withAnswer("196")
                            .withAnswer("236")
                            .withAnswer("266")
                            .withCorrectAnswer(2)
                            .withExtraInformation("15th-century, Gothic-style, former municipal headquarters with spires & 236 sculptures on the walls.")
                            .build())
                    .build();
            Game game2 = aGame()
                    .withName("Leuven Treasure Hunt2")
                    .withCity("Leuven")
                    .withCoordinates(50.8798, 4.7005)
                    .withDescription("Follow the map of questions and find the treasure!")
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879154, 4.704496)
                            .withQuestion("What is the main topic in the M-Museum")
                            .withAnswer("National History")
                            .withAnswer("Fashion")
                            .withAnswer("Modern art")
                            .withCorrectAnswer(3)
                            .withExtraInformation("Art museum opened in 2009, architect-designed to unite old buildings with contemporary architecture.")
                            .build())
                    .withQuestion(aQuestion()
                            .withCoordinates(50.879127, 4.701235)
                            .withQuestion("How many statues are on the walls?")
                            .withAnswer("196")
                            .withAnswer("236")
                            .withAnswer("266")
                            .withCorrectAnswer(2)
                            .withExtraInformation("15th-century, Gothic-style, former municipal headquarters with spires & 236 sculptures on the walls.")
                            .build())
                    .build();
            httpPost("/games", game1);
            httpPost("/games", game2);

            String actualGames = httpGet("games");
            String expectedGamesAsJson = jsonTestFile("testGetGames.json");

            assertThatJson(actualGames).isEqualTo(expectedGamesAsJson);
        }
    }
