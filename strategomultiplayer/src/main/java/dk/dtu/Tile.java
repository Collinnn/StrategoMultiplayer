package dk.dtu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle implements EventHandler<MouseEvent> {
    public static final int SIZE = 50;
    private int row, col;
    private StackPane stack;
    public Tile(int row, int column){
        this.row = row;
        this.col = col;
        Board.tiles[row][col] = this;

        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.ALICEBLUE);
        setStroke(Color.GRAY);
        stack = new StackPane(); // Idea to put picture on top of stackpane. Not known if works.
        Board.grid.add(stack, column, row);

    }
    @Override
    public void handle(MouseEvent event) {
        // TODO Auto-generated method stub
        
    }



}
