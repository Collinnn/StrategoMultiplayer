package dk.dtu;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TileController {

    public int x;
    public int y;

	public static void onClick(Tile tile, MouseEvent event) {
		if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
            tile.setHighlight(Color.BISQUE);
        }
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
            tile.setHighlight(null);
        }
        //Left Click
        if(event.getButton() == MouseButton.PRIMARY){
            this.x = tile.getCol();
            this.y = tile.getRow();
        }
        		
	}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
