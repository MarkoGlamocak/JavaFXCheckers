import javafx.scene.layout.VBox;

public class GameBoardTile extends VBox {
    private int row;
    private int column;
    private int color; // color = 0 means the tile is white, color = 1 means the tile is black.
    private int takenBy; // takenBy = 0 means the tile is not takenBy anyone, takenBy = 1 means the tile is taken by player 1, takenBy = 2 means the tile is taken by player 2.

    // GameButton Constructor
    public GameBoardTile(int x, int y, int z) {
        this.row = x;
        this.column = y;
        this.takenBy = 0;
        this.setColor(z);
        this.setPrefSize(100,100);
    }

    // Returns the row the VBox is in the gridpane
    public int getRow() {
        return this.row;
    }

    // Returns the column the VBox is in the gridpane
    public int getColumn() {
        return this.column;
    }

    public void setColor(int val) {
        this.color = val;
        if(val == 0) {
            this.setStyle("-fx-background-color: black");
        } else {
            this.setStyle("-fx-background-color: white");
        }
    }

    void setTakenBy(int val) {
        this.takenBy = val;
    }
}
