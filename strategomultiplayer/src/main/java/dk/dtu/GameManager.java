package dk.dtu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jspace.ActualField;
import org.jspace.FormalField;

public class GameManager implements Runnable {
	public static boolean clash = false;
	
	@Override
	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				String location = null;
				Piece piece = null;
				boolean piecePassed = false;
				boolean movePassed = false;
				int pieceX = 0;
				int pieceY = 0;
				Board.receiveClick = true;
				while(!piecePassed) {
					while(true){
						System.out.println("Click the piece you want to move");
						while(!Board.clicked) {
							Thread.sleep(1000);
							System.out.println(Board.clicked);
						}
						Board.clicked = false; 
						if(checkString(Board.currentSelectedTile)){
							pieceX = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
							pieceY = (int) Board.currentSelectedTile.charAt(2) -'0';
							piece = queryPiece(pieceX, pieceY);
							Board.currentSelectedTile = "-1,-1";
							if(piece != null){
								piecePassed = true;
								System.out.println(pieceX + "," + pieceY + "," + piece.getJpegKey());
								System.out.println("Piece is selected");
								break;
							} else System.out.println("Selected piece is not yours");
						} 
					}
				}
				while(!movePassed) {
					int z = -1;
					int w = -1;
					while(true){
						System.out.println("Click tile to move to (If you click the same tile it unselects)");
						while(!Board.clicked){
							Thread.sleep(1000);
							System.out.println(Board.clicked);
							System.out.println("waiting for second move");
						} 
						Board.clicked = false; 
						if(checkString(Board.currentSelectedTile)){
							z = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
							w = (int) Board.currentSelectedTile.charAt(2) -'0';
							Board.currentSelectedTile = "-1,-1";
							break;
						}
						Board.clicked = false; 
					}
					if(pieceX == z && pieceY==w){
						piecePassed = false;
						break; //Assume deselect
					}

					if(piece.getPieceType() == PieceType.BOMB){

					}
					else if(piece.getPieceType() == PieceType.SCOUT){
						switch((isStraight(pieceX, pieceY, z, w))){
							case LEGAL:
								movePiece(pieceX,pieceY,z,w,piece);
								movePassed = true;
								break;
							case BATTLE:
								//TODO: Battle
								break;
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
								movePiece(pieceX,pieceY,z,w,piece);
								movePassed = true;
								break;
							case BATTLE:
								//TODO: Battle
								break;
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
	
	
	public boolean checkString(String string) {
		if(!string.matches("[0-9]{1},[0-9]{1}")|| string.length() > 3) {
            System.out.println("Input wrong symbols or not in correct format");
            return false;
		}
        return true;
	}
	
	public Piece queryPiece(int x,int y) throws InterruptedException {
		//System.out.println(x);
		
		Object[] objects = Board.position.queryp(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		
		//System.out.println(piece[0] + "," + piece[1]);
		Piece piece = objects == null? null: (Piece)objects[2];
		if(piece == null || !piece.isOwner())return null;
		return piece;
	}
	
	public void movePiece(int x,int y, int z, int w, Piece piece) throws InterruptedException {
		Board.position.get(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		Board.position.put(z,w,piece);
		//System.out.println(z + "," + w);
		//Object[] temp = Board.position.queryp(new ActualField(z),new ActualField(w),new FormalField(Piece.class));
		//if(temp == null) System.out.println("This sucks");
		//else System.out.println(temp[0] + "," + temp[1]);
		Board.tiles[x][y].removePiece();
		Board.tiles[z][w].addPiece(piece);
	}
	
	public Move isNeighbor(int x,int y,int z,int w) throws InterruptedException {
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
	public Move isStraight(int x, int y, int z, int w) throws InterruptedException{
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

	public Move isClash(int z, int w) throws InterruptedException{
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

	
}