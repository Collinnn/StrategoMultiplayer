package dk.dtu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jspace.SequentialSpace;
import org.jspace.Space;
import org.jspace.Tuple;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Board {
    public static GridPane grid = new GridPane();
    private static String setup = "FL";
    public static int width = 10;
    public static int height = 10;
    public static Tile[][] tiles = new Tile[height][width];
    private static StackPane stack = new StackPane();
    private static Space position = new SequentialSpace();

    public Board(){
        grid.setPadding(new Insets(2));
        grid.setAlignment(Pos.CENTER);
        initTiles();
        // init Players();
    }
  
    public static void initTiles(){
        for(int row = 0; row<height; row++){
            for(int col = 0; col < width; col++){
                tiles[col][row] = new Tile(row,col);
            }
        }
        try {
            position.put(1,5,"FLAG");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initGamePieces();
    }
    
    public static void initGamePieces(){
        ArrayList<Tile> AllTiles = new ArrayList<>();
        for(Tile[] tileRow : tiles){
            AllTiles.addAll(Arrays.asList(tileRow));
        }
        int groupsize = AllTiles.size(); //Size of tiles
        int pieces = 40;
        Piece piece = new Piece(PieceType.FLAG, Color.RED, false);
        try {
            //Object[] position1 = position.getp();
            //if(position1 == null){
            //    return;
            //}else{
            List<Object[]> pieceList = position.queryAll();
            for (Object[] objects : pieceList) {
                tiles[(int)objects[0]][(int)objects[1]].addPiece(piece);
            }
            //}
            

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }


}