import javafx.application.Application;
import javafx.application.Platform;
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
    private int whichPlayer = 1;

    // Welcome Scene Data Members
    private Button singlePlayer;
    private Button multiPlayer;
    private Button howToPlay;
    private Button exitButton1;

    // Single Game Scene Data Members
    private GridPane gameBoard;

    // Result Scene Data Members
    private Button exitButton2;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        sceneMap = new HashMap<>();
        sceneMap.put("welcome", welcomeScene());
        sceneMap.put("single", singleGameScene());
        sceneMap.put("result", resultScene());

        singlePlayer.setOnAction(e -> stage.setScene(sceneMap.get("single")));

        exitButton1.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        exitButton2.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setScene(sceneMap.get("welcome"));
        stage.show();
    }

    public Scene welcomeScene() {
        Label welcomeLabel = new Label("The Game of Checkers");
        welcomeLabel.setFont(Font.font("Times New Roman", 70));
        singlePlayer = new Button("Single Player");
        singlePlayer.setFont(Font.font("Times New Roman", 30));
        multiPlayer = new Button("Multi Player");
        multiPlayer.setFont(Font.font("Times New Roman", 30));
        howToPlay = new Button("How To Play");
        howToPlay.setFont(Font.font("Times New Roman", 30));
        exitButton1 = new Button("Exit");
        exitButton1.setFont(Font.font("Times New Roman", 30));
        VBox root = new VBox(welcomeLabel, singlePlayer, multiPlayer, howToPlay, exitButton1);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        return new Scene(root, 900, 900);
    }

    public Scene singleGameScene() {
        gameBoardMatrix = new GameBoardTile[8][8];
        checkerMatrix = new Checker[8][8];
        gameBoard = new GridPane();
        addGrid(gameBoard);
        VBox root = new VBox(gameBoard);
        root.setAlignment(Pos.CENTER);
        return new Scene(root, 700, 700);
    }

    public Scene resultScene() {
        Label resultLabel = new Label("Player Blank Won!");
        resultLabel.setAlignment(Pos.CENTER);
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setAlignment(Pos.CENTER);
        exitButton2 = new Button("Exit");
        VBox root = new VBox(resultLabel, playAgainButton, exitButton2);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
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
                Checker checker = new Checker(j, i, 0);
                if (color == 0) {
                    color = 1;
                    checkerMatrix[j][i] = null;
                } else {
                    color = 0;
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
                /*
                checker.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        checker.
                    }
                });
                 */
                grid.add(gb, i, j); // Adds GameButton to GridPane
                gameBoardMatrix[j][i] = gb; // Adds GameButton to 2D Matrix Data Structure
            }
        }
    }
}