import javafx.scene.control.Button;

public class GameButton extends Button {
    private int row;
    private int column;
    private int color; // color = 0 means the tile is white, color = 1 means the tile is black.

    // GameButton Constructor
    public GameButton(int x, int y, int z) {
        this.row = x;
        this.column = y;
        this.setColor(z);
        this.setPrefSize(100,100);
    }

    // Returns the row the button is in the gridpane
    public int getRow() {
        return this.row;
    }

    // Returns the column the button is in the gridpane
    public int getColumn() {
        return this.column;
    }

    public void setColor(int val) {
        this.color = val;
        if(val == 0) {
            this.setStyle("-fx-background-color: white");
        } else {
            this.setStyle("-fx-background-color: black");
        }
    }
}
