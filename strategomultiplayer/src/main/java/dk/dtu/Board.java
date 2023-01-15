package dk.dtu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Board {
    public static GridPane grid = new GridPane();
    public static int width = 10;
    public static int height = 10;
    public static Tile[][] tiles = new Tile[height][width];
    private static StackPane stack = new StackPane();
    public static Space position = new SequentialSpace();
    public static String currentSelectedTile = "-1,-1";
    public static boolean receiveClick;
    public static boolean clicked = false;
    public static boolean bottomPlayer = true;

    public Board(Boolean bool){
    	bottomPlayer = bool;
        grid.setPadding(new Insets(2));
        grid.setAlignment(Pos.CENTER);
        initTiles();
        // init Players();
    }
  
    public static void initTiles(){
        for(int row = 0; row<height; row++){
            for(int col = 0; col < width; col++){
                tiles[col][row] = new Tile(col,row);
            }
        }
        initGamePieces();
    }
    
    public static void initGamePieces(){
    	String defaultString = "2237BB44BB98S3686674B1053376555FB22222234";
        int stringLength = 40;
    	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Input initial setup of pieces as 40 character string");
    	String string = null;
    	try {
			string = input.readLine();
			if(string.length()>stringLength) {
				System.out.println("String is too long");
				string = null;
			} else if(string.length() <stringLength){
				System.out.println("String is shorter than maximum using partial piece placements");
				string = null;
			} else {
				if(string.matches("^[SBF02-9]+$")) {
					System.out.println("String works");
				} else {
					System.out.println("String uses characters other than B F S 0 1 2 3 4 5 6 7 8 9");
					string = null;
				}
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	if(string == null) {
    		System.out.println("Using default setup");
    		string = defaultString;
    	}
    	
    	int playerRow = 0;
    	int enemyRow = 6;
    	Color playerColor = Color.RED;
    	Color enemyColor = Color.BLUE;
    	if(bottomPlayer) {
    		playerRow = 6;
    		enemyRow = 0;
    		playerColor = Color.BLUE;
        	enemyColor = Color.RED;
    	}
    	
    	
    	
    	for(int r = playerRow; r < playerRow+4; r++) {
    		for(int col = 0; col<10; col++) {
    			try {
					position.put(col,r,new Piece(pieceSwitch(string.charAt(((r - playerRow)*10)+col)),playerColor,false));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    	for(int r = enemyRow; r < enemyRow+4; r++) {
    		for(int col = 0; col<10; col++) {
    			try {
					position.put(col,r,new Piece(PieceType.UNKOWN,enemyColor,true));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    	
        try {
        	System.out.println("Hi");
            List<Object[]> pieceList = position.queryAll(new FormalField(Integer.class),new FormalField(Integer.class),new FormalField(Piece.class));
            System.out.println(pieceList.size()); 
            for (Object[] objects : pieceList) {
                tiles[(int)objects[0]][(int)objects[1]].addPiece((Piece)objects[2]);
                System.out.println("(" +(int)objects[0] + "," + (int)objects[1]+ ") " + (Piece)objects[2]);
            }
            System.out.println("Goodbye");

            

        } catch (InterruptedException e) {
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