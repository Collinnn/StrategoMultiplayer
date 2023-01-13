package dk.dtu;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import javafx.event.EventHandler;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle implements EventHandler<MouseEvent> {
    public static final int SIZE = 50;
    public boolean isSelected = false;
    private int row, col;

    private StackPane stack;
    private Piece piece = null;
    private Bloom bloom = new Bloom();
    private final InnerShadow innerShadow = new InnerShadow(3,Color.BISQUE);
    private Effect highlight;
    public Tile(int col, int row){
        this.row = row;
        this.col = col;
        Board.tiles[col][row] = this;
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.TRANSPARENT);
        setStroke(Color.GRAY);
        stack = new StackPane(); // Idea to put picture on top of stackpane. Not known if works.
        stack.getChildren().add(this);
        Board.grid.add(stack, col, row);

        innerShadow.setRadius(3.5);
        innerShadow.setChoke(2); 
        bloom.setInput(innerShadow);
        highlight = bloom;
        setOnMouseClicked(this);
		setOnMouseEntered(this);
		setOnMouseExited(this);
    }
    @Override
    public void handle(MouseEvent event) {
        TileController.onClick(this,event);
              
       
        
    }

    public void addPiece(Piece piece){
        String jpegKey = piece.getJpegKey();
        //setFill(new ImagePattern(new Image(jpegKey +".png")));
        this.piece = piece;
        setFill(piece.getPieceColor());
        //stack = new StackPane(); // Idea to put picture on top of stackpane. Not known if works.
        //stack.getChildren().add(this);
        //Board.grid.add(stack, col, row);
        
    }
    
    public boolean hasPiece() {
    	return this.piece != null? true : false;
    }
    
    public Piece getPiece() {
    	return this.piece;
    }

    public void removePiece(){
        this.piece = null;
        setFill(Color.TRANSPARENT);
    }
	public void setHighlight(Color color) {
		if(color == null){
            setEffect(null);
        } else {
            innerShadow.setColor(color);
            setEffect(highlight);
        }
		
	}
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
	public void selectTile() {
    	this.isSelected = true;
    	this.setHighlight(Color.ORANGE);
    }
    
    public void deSelectTile() {
    	this.isSelected = false;
    	this.setHighlight(Color.BISQUE);
    }


}
