package dk.dtu;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TileController {

	public static void onClick(Tile tile, MouseEvent event) {
		if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
			if(!tile.isSelected) {
				tile.setHighlight(Color.BISQUE);
			}
        }
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
        	if(!tile.isSelected) {
        		tile.setHighlight(null);
        	}
        }
        //Left Click
        if(event.getButton() == MouseButton.PRIMARY){
            if(Board.receiveClick){
                Board.currentSelectedTile = tile.getCol() + "," + tile.getRow();
                Board.clicked = true;
            }
        }
        		
	}
}
