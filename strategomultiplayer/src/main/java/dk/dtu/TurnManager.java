package dk.dtu;

import org.jspace.ActualField;
import org.jspace.FormalField;

import javafx.application.Platform;

public class TurnManager {
	
	public static PieceMoves move(){
		while(true){
			try {
				String location = null;
				Piece piece = null;
				Piece defender = null;
				boolean piecePassed = false;
				boolean movePassed = false;
				int pieceX = 0;
				int pieceY = 0;
				Board.receiveClick = true;
				while(!piecePassed) {
						System.out.println("Click the piece you want to move");
						while(!Board.clicked){
							Thread.sleep(1); // Stops locking the thread leading to unexpected behavior.
						} 
						Board.clicked = false; 

						pieceX = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
						pieceY = (int) Board.currentSelectedTile.charAt(2) -'0';
						piece = queryPiece(pieceX, pieceY);
						System.out.println(piece.getPieceType().getValue());
						Board.currentSelectedTile = "-1,-1";
						if(piece == null || !piece.isOwner() ){
							System.out.println("Selected piece is not yours");
						} else if(piece.getPieceType() == PieceType.BOMB || piece.getPieceType() == PieceType.FLAG){
							System.out.println("Selected piece is a Bomb or Flag and can therefore not move");
						}else{
							Board.tiles[pieceX][pieceY].selectTile();
							piecePassed = true;
							System.out.println(pieceX + "," + pieceY + "," + piece.getJpegKey());
							System.out.println("Piece is selected");
						} 
				}
				while(!movePassed) {
					//Z and W are the coordinates where the piece will move to 
					int z = -1;
					int w = -1;

					System.out.println("Click tile to move to (If you click the same tile it unselects)");
					while(!Board.clicked){
						Thread.sleep(1);
					} 
					Board.clicked = false; 
					z = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
					w = (int) Board.currentSelectedTile.charAt(2) -'0';
					Board.currentSelectedTile = "-1,-1";


					if(pieceX == z && pieceY==w){
						Board.tiles[pieceX][pieceY].deSelectTile();
						piecePassed = false;
						break; //Assume deselect
					}

					if(piece.getPieceType() == PieceType.SCOUT){
						switch((isStraight(pieceX, pieceY, z, w))){
							case LEGAL:
								Board.tiles[pieceX][pieceY].deSelectTile();
								System.out.println("About to send pieceMove");
								return new PieceMoves(pieceX,pieceY,z,w,piece,Move.LEGAL);
							case BATTLE:
								Board.tiles[pieceX][pieceY].deSelectTile();
								return new PieceMoves(pieceX,pieceY,z,w,piece,Move.BATTLE);
							default:
								System.out.println("Mime");
								System.out.println(isStraight(pieceX, pieceY, z, w));
								System.out.println("Illegal Move");
								break;
						}
					}
					else{
						switch((isNeighbor(pieceX, pieceY, z, w))){
							case LEGAL:
								Board.tiles[pieceX][pieceY].deSelectTile();
								return new PieceMoves(pieceX,pieceY,z,w,piece,Move.LEGAL);
							case BATTLE:
								Board.tiles[pieceX][pieceY].deSelectTile();
								return new PieceMoves(pieceX,pieceY,z,w,piece,Move.BATTLE);
							default:
								System.out.println("Meme");
								System.out.println(isNeighbor(pieceX, pieceY, z, w));
								System.out.println("Illegal Move");
								break;
						}
					}
				}
		
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("It broke");
			}
		} 		
	}
	
	public static void battle(Piece attacker,Piece defender, int x, int y, int z, int w) throws InterruptedException{
		System.out.println(defender.getPieceType().getValue());
		System.out.println(attacker.getPieceType().attacks(defender.getPieceType()));
		switch(attacker.getPieceType().attacks(defender.getPieceType())){
			case VICTORY:
				Board.position.get(new ActualField(z), new ActualField(w), new FormalField(Piece.class));
				movePiece(x, y, z, w, attacker);
				break;
			case MUTUALDEFEAT:
				removePiece(x,y);
				removePiece(z,w);
				break;
			case DEFEAT:
				removePiece(x,y);
				break;
		}
	}

	public boolean checkString(String string) {
		if(!string.matches("[0-9]{1},[0-9]{1}")|| string.length() > 3) {
            System.out.println("Input wrong symbols or not in correct format");
            return false;
		}
        return true;
	}
	
	public static Piece queryPiece(int x,int y) throws InterruptedException {
		//System.out.println(x);
		System.out.println(x + "," + y);
		Object[] objects = Board.position.queryp(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		
		//System.out.println(piece[0] + "," + piece[1]);
		Piece piece = objects == null? null: (Piece)objects[2];
		return piece;
	}
	
	public static void movePiece(int x,int y, int z, int w, Piece piece) throws InterruptedException {
		Board.position.get(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		Board.position.put(z,w,piece);
		updatePieceMove(x,y,z,w,piece);
		
	}
	
	public static void removePiece(int x,int y) throws InterruptedException {
		Board.position.get(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		updatePieceRemove(x,y);
		
	}
	
	public static void revealPiece(int x, int y, Piece piece) throws InterruptedException {
		Board.position.get(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		Board.position.put(x,y,piece);
		Platform.runLater(new Runnable() {
			public void run() {
				Board.tiles[x][y].addPiece(piece);
				}
		});
	}
	
	public static Move isNeighbor(int x,int y,int z,int w) throws InterruptedException {
		if(!(x==z && (y +1 == w || y-1 == w) || y== w && (x+1 == z || x-1 == z))){
			return Move.ILLEGAL;
		}
		System.out.println(isClash(z, w));
		switch(isClash(z, w)){
			case NOPIECE:
				return Move.LEGAL;
			case ENEMY:
				return Move.BATTLE;
			default:
				return Move.ILLEGAL;
		}
	}
	
	//Special rules for scout
	public static Move isStraight(int x, int y, int z, int w) throws InterruptedException{
		if((x!=z && y==w)){
			int start = x < z ? x+1 : z+1; 
			int end   = x < z ? z-1 : x-1; 
			for(int i = start;i < end;i++){
				switch(isClash(i, y)){
					case NOPIECE:
						System.out.println(i + "," + y);
						break;
					default:
						System.out.println(i + "," + y);
						return Move.ILLEGAL;
				}
			}
		} else if((x==z) && (y!=w)){
			int start = y < w ? y+1: w+1;
			int end   = y < w ? w-1: y-1;
			for(int i = start;i < end;i++){
				switch(isClash(x, i)){
					case NOPIECE:
						System.out.println(x + "," + i);
						break;
					default:
						System.out.println(x + "," + i);
						return Move.ILLEGAL;
				}
			}
		} else{
			System.out.println("The piece is not moving in a straight line");
			return Move.ILLEGAL;
		}
		switch(isClash(z, w)){
			case NOPIECE:
				return Move.LEGAL;
			case ENEMY:
				return Move.BATTLE;
			default:
				return Move.ILLEGAL;
		}
	}

	public static Move isClash(int z, int w) throws InterruptedException{
		Object[] check = Board.position.queryp(new ActualField(z),new ActualField(w),new FormalField(Piece.class));
		if(check != null){
			Piece piece = (Piece)check[2];
			if(!piece.isOwner()){
				System.out.println("Enemy attacked");
				return Move.ENEMY;
			}
			System.out.println("Friend Attacked");
			return Move.ILLEGAL;
		} 
		System.out.println("No piece just move");
		return Move.NOPIECE;
	}
	
	public static void updatePieceMove(int x,int y, int z, int w, Piece piece) {
		Platform.runLater(new Runnable() {
			public void run() {
				Board.tiles[x][y].removePiece();
				Board.tiles[z][w].addPiece(piece);
				}
		});
	}
	
	public static void updatePieceRemove(int x,int y) {
		Platform.runLater(new Runnable() {
			public void run() {
				Board.tiles[x][y].removePiece();
				}
		});
	}
	
}