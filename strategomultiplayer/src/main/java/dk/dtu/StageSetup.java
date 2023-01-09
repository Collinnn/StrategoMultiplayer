package dk.dtu;

import javafx.scene.layout.BorderPane;

public class StageSetup {
    public static BorderPane getStage(){
        BorderPane map = new BorderPane();
        map.setCenter(Board.grid);

        return map;
    }
}
