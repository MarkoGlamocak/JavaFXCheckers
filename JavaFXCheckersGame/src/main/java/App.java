import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {

    private HashMap<String, Scene> sceneMap;

    // Welcome Scene Data Fields
    private Button playGameButton;

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
        Label gameLabel = new Label("This is the game scene");
        VBox root = new VBox(gameLabel);
        return new Scene(root, 700, 700);
    }
}