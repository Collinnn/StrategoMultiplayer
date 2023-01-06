package dk.dtu;

import java.lang.reflect.WildcardType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Board {
    public static GridPane grid = new GridPane();

    public static int width = 16;
    public static int height = 16;
    public static GamePiece[][] tiles = new GamePiece[height][width];
    public Board(){
        grid.setPadding(new Insets(2));
        grid.setAlignment(Pos.CENTER);
        initTiles();
        // init Players();
    }
    public static void initTiles(){
        for(int row = 0; row<height; row++){
            for(int col = 0; col < width; col++){
                //New piece do the math. 
            }
        }
    }
}