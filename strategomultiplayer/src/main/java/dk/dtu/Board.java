package dk.dtu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jspace.SequentialSpace;
import org.jspace.Space;

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
    	String defaultString = "S112344556";
    	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Input initial setup of pieces as 40 character string");
    	String string = null;
    	try {
			string = input.readLine();
			if(string.length()>10) {
				System.out.println("String is too long");
				string = null;
			} else if(string.length() <10){
				System.out.println("String is shorter than maximum using partial piece placements");
				string = null;
			} else {
				if(string.matches("^[SBF0-9]+$")) {
					System.out.println("String works");
				} else {
					System.out.println("String uses characters other than B F S 0 1 2 3 4 5 6 7 8 9");
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if(string == null) {
    		string = defaultString;
    	}
    	
    	for(int row = 0; row < 4; row++) {
    		for(int col = 0; col<10; col++) {
    			
    		}
    	}
    	
    	
    	
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
    
    
    public static PieceType pieceSwitch(char c) {
    	switch(c) {
    		case 'S': return PieceType.SPY;
    		case 'F': return PieceType.FLAG;
    		case 'B': return PieceType.BOMB;
    		case '2': return PieceType.SCOUT;
    		case '3': return PieceType.MINER;
    		case '4': return PieceType.SERGEANT;
    		case '5': return PieceType.LIEUTENANT;
    		case '6': return PieceType.CAPTAIN;
    		case '7': return PieceType.MAJOR;
    		case '8': return PieceType.COLONEL;
    		case '9': return PieceType.GENERAL;
    		case '0': return PieceType.MARSHAL;
    	}
    	
    	
    	return PieceType.FLAG;
    }


}