import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {

    private HashMap<String, Scene> sceneMap;
    private GameBoardTile[][] gameBoardMatrix;
    private Checker[][] checkerMatrix;

    // Welcome Scene Data Members
    private Button playGameButton;

    // Game Scene Data Members
    private GridPane gameBoard;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        sceneMap = new HashMap<>();
        sceneMap.put("welcome", welcomeScene());
        sceneMap.put("game", gameScene());

        playGameButton.setOnAction(e -> stage.setScene(sceneMap.get("game")));

        stage.setScene(sceneMap.get("welcome"));
        stage.show();
    }

    public Scene welcomeScene() {
        Label welcomeLabel = new Label("The Game of Checkers");
        playGameButton = new Button("Play Game");
        VBox root = new VBox(welcomeLabel, playGameButton);
        root.setAlignment(Pos.CENTER);
        return new Scene(root, 900, 900);
    }

    public Scene gameScene() {
        gameBoardMatrix = new GameBoardTile[8][8];
        checkerMatrix = new Checker[8][8];
        gameBoard = new GridPane();
        addGrid(gameBoard);
        VBox root = new VBox(gameBoard);
        root.setAlignment(Pos.CENTER);
        return new Scene(root, 700, 700);
    }

    // This function adds game buttons to the GridPane layout and attaches event handlers to each button, so that they are intractable with.
    public void addGrid(GridPane grid) {
        int color = 0;
        for (int i = 0; i < 8; i++) {
            if (color == 0) {
                color = 1;
            } else {
                color = 0;
            }
            for (int j = 0; j < 8; j++) {
                GameBoardTile gb = new GameBoardTile(j, i, color);
                if (color == 0) {
                    color = 1;
                    checkerMatrix[j][i] = null;
                } else {
                    color = 0;
                    Checker checker = new Checker(j, i, 0);
                    gb.getChildren().add(checker);
                    gb.setAlignment(Pos.CENTER);
                    if (j < 3) {
                        gb.setTakenBy(1);
                        checker.setColor(1);
                    } else if (j > 4) {
                        gb.setTakenBy(2);
                        checker.setColor(2);
                    } else {
                        checker.setColor(0);
                    }
                }
                /*gb.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Nothing For Now!
                    }
                });*/
                grid.add(gb, i, j); // Adds GameButton to GridPane
                gameBoardMatrix[j][i] = gb; // Adds GameButton to 2D Matrix Data Structure
            }
        }
    }
}