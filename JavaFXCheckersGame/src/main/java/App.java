import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX App
 */
public class App extends Application {

    private HashMap<String, Scene> sceneMap;
    private GameBoardTile[][] gameBoardMatrix;
    private Checker[][] checkerMatrix;
    private int whichPlayer = 1;
    private int lastPlayerToMove = 1;
    private int[] checkerLocation;
    private Checker lastChecker;
    private boolean checkerIsSelected = false;
    private boolean canMoveAgain = true;

    private int numRed = 12;
    private int numBlue = 12;
    private PauseTransition pause;
    private Timer timer;
    private TimerTask timeTask;

    // Welcome Scene Data Members
    private Button singlePlayer;
    private Button multiPlayer;
    private Button howToPlay;
    private Button exitButton1;

    // Single Game Scene Data Members
    private GridPane gameBoard;
    private Label turnTracker;
    private Button endTurn;
    private Label timerLabel;
    private Button startGameButton;

    // How To Play Scene Data Members
    private Button goBackButton;

    // Result Scene Data Members
    private Button exitButton2;
    private Label resultLabel;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        sceneMap = new HashMap<>();
        sceneMap.put("welcome", welcomeScene());
        sceneMap.put("single", singleGameScene());
        sceneMap.put("howTo", howToPlayScene());
        sceneMap.put("result", resultScene());

        singlePlayer.setOnAction(e -> stage.setScene(sceneMap.get("single")));

        timer = new Timer();
        timeTask = new TimerTask() {
            @Override
            public void run() {
                if (lastChecker != null && lastChecker.isSelected()) {
                    lastChecker.unselectChecker();
                }
                checkerIsSelected = false;
                canMoveAgain = true;
                if (lastChecker.getColor() == 1) {
                    whichPlayer = 2;
                    turnTracker.setText("Blue's Turn");
                    turnTracker.setStyle("-fx-background-color: blue");
                } else {
                    whichPlayer = 1;
                    turnTracker.setText("Red's Turn");
                    turnTracker.setStyle("-fx-background-color: red");
                }
            }
        };

        pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> { stage.setScene(sceneMap.get("result"));
                                    resultLabel.setText("Player " + lastChecker.getColor() + " Won!");
        });

        startGameButton.setOnAction(e -> {
            startGameButton.setVisible(false);
            startGameButton.setDisable(true);
        });

        exitButton1.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        exitButton2.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        howToPlay.setOnAction(e -> stage.setScene(sceneMap.get("howTo")));
        goBackButton.setOnAction(e -> stage.setScene(sceneMap.get("welcome")));

        endTurn.setOnAction(e -> {  lastChecker.unselectChecker();
                                    checkerIsSelected = false;
                                    canMoveAgain = true;
                                    if (lastChecker.getColor() == 1) {
                                        whichPlayer = 2;
                                        turnTracker.setText("Blue's Turn");
                                        turnTracker.setStyle("-fx-background-color: blue");
                                    } else {
                                        whichPlayer = 1;
                                        turnTracker.setText("Red's Turn");
                                        turnTracker.setStyle("-fx-background-color: red");
                                    }
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
        root.setStyle("-fx-background-image: url(checkered-board-game.jpg);" + "-fx-background-repeat: stretch;" + "-fx-background-size: 900 900;" + "-fx-background-position: center center;");
        return new Scene(root, 900, 900);
    }

    public Scene singleGameScene() {
        gameBoardMatrix = new GameBoardTile[8][8];
        checkerMatrix = new Checker[8][8];
        checkerLocation = new int[2];
        gameBoard = new GridPane();
        addGrid(gameBoard);
        timerLabel = new Label("5:00");
        turnTracker = new Label("Red's Turn");
        turnTracker.setStyle("-fx-background-color: red");
        endTurn = new Button("End Turn");
        startGameButton = new Button("Start");
        startGameButton.setFont(Font.font("Times New Roman", 70));
        VBox root1 = new VBox(timerLabel, turnTracker, endTurn);
        root1.setAlignment(Pos.CENTER);
        root1.setPrefSize(200, 50);
        root1.setSpacing(15);
        HBox root2 = new HBox(root1, gameBoard);
        root2.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(root2, startGameButton);
        root.setAlignment(Pos.CENTER);
        return new Scene(root, 800, 800);
    }

    public Scene howToPlayScene() {
        Label titleHowTo = new Label("How To Play");
        Label instructions = new Label("Instructions");
        goBackButton = new Button("Go Back");
        VBox root = new VBox(titleHowTo, instructions, goBackButton);
        return new Scene(root, 700, 700);
    }

    public Scene resultScene() {
        resultLabel = new Label();
        resultLabel.setAlignment(Pos.CENTER);
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setAlignment(Pos.CENTER);
        exitButton2 = new Button("Exit");
        VBox root = new VBox(resultLabel, playAgainButton, exitButton2);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.setStyle("-fx-background-image: url(checkered-board-game.jpg);" + "-fx-background-repeat: stretch;" + "-fx-background-size: 700 700;" + "-fx-background-position: center center;");
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
                } else {
                    color = 0;
                }
                checker.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (checker.isValid(whichPlayer)) {
                            if (whichPlayer == 1) {
                                lastPlayerToMove = 1;
                                whichPlayer = 0;
                                checkerLocation[0] = checker.getRow();
                                checkerLocation[1] = checker.getColumn();
                                lastChecker = checker;
                                checker.selectChecker();
                            } else if (whichPlayer == 2) {
                                lastPlayerToMove = 2;
                                whichPlayer = 0;
                                checkerLocation[0] = checker.getRow();
                                checkerLocation[1] = checker.getColumn();
                                lastChecker = checker;
                                checker.selectChecker();
                            } else {
                                if (lastPlayerToMove == 1 && checker.getColor() == 1 && !checkerIsSelected) {
                                    checkerLocation[0] = checker.getRow();
                                    checkerLocation[1] = checker.getColumn();
                                    lastChecker.unselectChecker();
                                    lastChecker = checker;
                                    checker.selectChecker();
                                } else if (lastPlayerToMove == 2 && checker.getColor() == 2 && !checkerIsSelected) {
                                    checkerLocation[0] = checker.getRow();
                                    checkerLocation[1] = checker.getColumn();
                                    lastChecker.unselectChecker();
                                    lastChecker = checker;
                                    checker.selectChecker();
                                } else if (lastPlayerToMove == 1 && checker.getColor() == 0) {
                                    if (checker.getRow() == lastChecker.getRow() + 1 && Math.abs(checker.getColumn() - lastChecker.getColumn()) == 1 && !checkerIsSelected) {
                                        checker.setColor(1);
                                        checker.selectChecker();
                                        checker.makeKing(lastChecker);
                                        checkerIsSelected = true;
                                        lastChecker.setColor(0);
                                        lastChecker.unselectChecker();
                                        lastChecker.destroyKing();
                                        lastChecker = checker;
                                    }
                                    if (checker.getRow() == lastChecker.getRow() - 1 && Math.abs(checker.getColumn() - lastChecker.getColumn()) == 1 && !checkerIsSelected && lastChecker.isKing()) {
                                        checker.setColor(1);
                                        checker.selectChecker();
                                        checker.makeKing(lastChecker);
                                        checkerIsSelected = true;
                                        lastChecker.setColor(0);
                                        lastChecker.unselectChecker();
                                        lastChecker.destroyKing();
                                        lastChecker = checker;
                                    }
                                    if (lastChecker.getRow() + 2 <= 7 && lastChecker.getColumn() - 2 >= 0 && canMoveAgain) {
                                        if (checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].getColor() == 2 && checkerMatrix[lastChecker.getRow() + 2][lastChecker.getColumn() - 2] == checker) {
                                            checker.setColor(1);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            canMoveAgain = false;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].destroyKing();
                                            numBlue--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() + 2 <= 7 && lastChecker.getColumn() + 2 <= 7 && canMoveAgain) {
                                        if (checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].getColor() == 2 && checkerMatrix[lastChecker.getRow() + 2][lastChecker.getColumn() + 2] == checker) {
                                            checker.setColor(1);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].destroyKing();
                                            numBlue--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() - 2 >= 0 && lastChecker.getColumn() - 2 >= 0 && canMoveAgain && lastChecker.isKing()) {
                                        if (checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].getColor() == 2 && checkerMatrix[lastChecker.getRow() - 2][lastChecker.getColumn() - 2] == checker) {
                                            checker.setColor(1);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            canMoveAgain = false;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].destroyKing();
                                            numBlue--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() - 2 >= 0 && lastChecker.getColumn() + 2 <= 7 && canMoveAgain && lastChecker.isKing()) {
                                        if (checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].getColor() == 2 && checkerMatrix[lastChecker.getRow() - 2][lastChecker.getColumn() + 2] == checker) {
                                            checker.setColor(1);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            canMoveAgain = false;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].destroyKing();
                                            numBlue--;
                                            lastChecker = checker;
                                        }
                                    }
                                } else if (lastPlayerToMove == 2 && checker.getColor() == 0) {
                                    if (checker.getRow() == lastChecker.getRow() - 1 && Math.abs(checker.getColumn() - lastChecker.getColumn()) == 1 && !checkerIsSelected) {
                                        checker.setColor(2);
                                        checker.selectChecker();
                                        checker.makeKing(lastChecker);
                                        checkerIsSelected = true;
                                        canMoveAgain = false;
                                        lastChecker.setColor(0);
                                        lastChecker.unselectChecker();
                                        lastChecker.destroyKing();
                                        lastChecker = checker;
                                    }
                                    if (checker.getRow() == lastChecker.getRow() + 1 && Math.abs(checker.getColumn() - lastChecker.getColumn()) == 1 && !checkerIsSelected && lastChecker.isKing()) {
                                        checker.setColor(2);
                                        checker.selectChecker();
                                        checker.makeKing(lastChecker);
                                        checkerIsSelected = true;
                                        canMoveAgain = false;
                                        lastChecker.setColor(0);
                                        lastChecker.unselectChecker();
                                        lastChecker.destroyKing();
                                        lastChecker = checker;
                                    }
                                    if (lastChecker.getRow() - 2 >= 0 && lastChecker.getColumn() - 2 >= 0 && canMoveAgain) {
                                        if (checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].getColor() == 1 && checkerMatrix[lastChecker.getRow() - 2][lastChecker.getColumn() - 2] == checker) {
                                            checker.setColor(2);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() - 1].destroyKing();
                                            numRed--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() - 2 >= 0 && lastChecker.getColumn() + 2 <= 7 && canMoveAgain) {
                                        if (checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].getColor() == 1 && checkerMatrix[lastChecker.getRow() - 2][lastChecker.getColumn() + 2] == checker) {
                                            checker.setColor(2);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() - 1][lastChecker.getColumn() + 1].destroyKing();
                                            numRed--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() + 2 <= 7 && lastChecker.getColumn() - 2 >= 0 && canMoveAgain && lastChecker.isKing()) {
                                        if (checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].getColor() == 1 && checkerMatrix[lastChecker.getRow() + 2][lastChecker.getColumn() - 2] == checker) {
                                            checker.setColor(2);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() - 1].destroyKing();
                                            numRed--;
                                            lastChecker = checker;
                                        }
                                    }
                                    if (lastChecker.getRow() + 2 <= 7 && lastChecker.getColumn() + 2 <= 7 && canMoveAgain && lastChecker.isKing()) {
                                        if (checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].getColor() == 1 && checkerMatrix[lastChecker.getRow() + 2][lastChecker.getColumn() + 2] == checker) {
                                            checker.setColor(2);
                                            checker.selectChecker();
                                            checker.makeKing(lastChecker);
                                            checkerIsSelected = true;
                                            lastChecker.setColor(0);
                                            lastChecker.unselectChecker();
                                            lastChecker.destroyKing();
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].setColor(0);
                                            checkerMatrix[lastChecker.getRow() + 1][lastChecker.getColumn() + 1].destroyKing();
                                            numRed--;
                                            lastChecker = checker;
                                        }
                                    }
                                }

                                }
                            }
                        if (isWin1()) {
                            pause.play();
                        }
                    }
                });
                grid.add(gb, i, j); // Adds GameButton to GridPane
                gameBoardMatrix[j][i] = gb; // Adds GameButton to 2D Matrix Data Structure
                checkerMatrix[j][i] = checker; // Adds Checker to 2D Matrix Data Structure
            }
        }
    }

    boolean isWin1() {
        /////
        ///// ALSOOOOOOO Check if a player can't move. If they can't then they lose.
        /////
        if (numRed == 0 || numBlue == 0) {
            return true;
        }
        return false;
    }

    /*
    boolean isWin2() {
        for (Checker[] a : checkerMatrix) {
            for (Checker e : a) {
                if (e.getRow() + 1 <= 7 && ) {

                }
            }
        }
        return true;
    }

     */
}
