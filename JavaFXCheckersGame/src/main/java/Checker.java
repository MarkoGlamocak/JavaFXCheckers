import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class Checker extends Button {
    private int row;
    private int column;
    private int color; // color = 0 means the checker is white, color = 1 means the checker is red, color = 2 means the tile is blue.

    Checker(int x, int y, int z) {
        this.row = x;
        this.column = y;
        this.color = z;
        double r = 30;
        this.setShape(new Circle(r));
        this.setMinSize(2*r, 2*r);
        this.setMaxSize(2*r, 2*r);
    }

    void setColor(int val) {
        this.color = val;
        if (val == 0) {
            this.setStyle("-fx-background-color: black");
        } else if (val == 1) {
            this.setStyle("-fx-background-color: red");
        } else {
            this.setStyle("-fx-background-color: blue");
        }
    }

    boolean isValid(int val) {
        if (color == val) {
            return true;
        }
        return false;
    }

    // Returns the row the Checker is in the gridpane
    public int getRow() {
        return this.row;
    }

    // Returns the column the Checker is in the gridpane
    public int getColumn() {
        return this.column;
    }

    // Returns the Checker Color, this is how we can identify the who the checker belongs to
    public int getColor() {
        return this.color;
    }
}
