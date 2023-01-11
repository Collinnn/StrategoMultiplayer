package dk.dtu;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle implements EventHandler<MouseEvent> {
    public static final int SIZE = 50;
    private int row, col;
    private StackPane stack;
    private Piece piece = null;
    public Tile(int row, int col){
        this.row = row;
        this.col = col;
        Board.tiles[row][col] = this;
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.TRANSPARENT);
        setStroke(Color.GRAY);
        stack = new StackPane(); // Idea to put picture on top of stackpane. Not known if works.
        stack.getChildren().add(this);
        Board.grid.add(stack, col, row);

    }
    @Override
    public void handle(MouseEvent event) {
        // TODO Auto-generated method stub
        
    }

    public void addPiece(Piece piece){
        String jpegKey = piece.getJpegKey();
        setFill(new ImagePattern(new Image(jpegKey +".png")));
        this.piece = piece;
        setFill(Color.RED);
        stack = new StackPane(); // Idea to put picture on top of stackpane. Not known if works.
        stack.getChildren().add(this);
        Board.grid.add(stack, col, row);
        
    }

    public void removePiece(){
        this.piece = null;
        setFill(Color.TRANSPARENT);
    }



}
