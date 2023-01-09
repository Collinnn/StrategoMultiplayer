package dk.dtu;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Board {
    public static GridPane grid = new GridPane();

    public static int width = 10;
    public static int height = 10;
    public static Tile[][] tiles = new Tile[height][width];
    private static StackPane stack = new StackPane();
    public Board(){
        grid.setPadding(new Insets(2));
        grid.setAlignment(Pos.CENTER);
        initTiles();
        // init Players();
    }
    public static void initTiles(){
        for(int row = 0; row<height; row++){
            for(int col = 0; col < width; col++){
                new Tile(row,col);
            }
        }
    }
    public static void initGamePieces(){
        ArrayList<Tile> AllTiles = new ArrayList<>();
        for(Tile[] tileRow : tiles){
            AllTiles.addAll(Arrays.asList(tileRow));
        }
        int groupsize = AllTiles.size(); //Size of tiles
        int pieces = 40;
    }


}