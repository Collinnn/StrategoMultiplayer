package dk.dtu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jspace.ActualField;
import org.jspace.FormalField;

public class GameManager implements Runnable {

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
				while(!piecePassed) {
					System.out.println("Input the location of the piece you want to move in this format x,y");
					location = input.readLine();
					if(!checkString(location));
					else {
						pieceX = (int) location.charAt(0) -'0'; //Forces it to be an string
						pieceY = (int) location.charAt(2) -'0'; //Because direct translation makes the char value ASCII
						piece = queryPiece(pieceX,pieceY);
						if(piece != null) {
							if(piece.isOwner()) piecePassed = true;
							else System.out.println("This is not your piece");
						} else System.out.println("There is no piece here");
					}
				}
				while(!movePassed) {
					System.out.println("Input tile to move to");
					String move = input.readLine();
					if(!checkString(move)) System.out.println("It broke here");
					else {
						int z = (int) move.charAt(0) -'0';
						int w = (int) move.charAt(2) -'0';
						movePiece(pieceX,pieceY,z,w,piece);
						movePassed = true;
						
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
		
		Object[] piece = Board.position.queryp(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		
		//System.out.println(piece[0] + "," + piece[1]);
		return piece == null? null: (Piece)piece[2];
	}
	
	public void movePiece(int x,int y, int z, int w, Piece piece) throws InterruptedException {
		Board.position.get(new ActualField(x),new ActualField(y),new FormalField(Piece.class));
		Board.position.put(z,w,piece);
		System.out.println(z + "," + w);
		//Object[] temp = Board.position.queryp(new ActualField(z),new ActualField(w),new FormalField(Piece.class));
		//if(temp == null) System.out.println("This sucks");
		//else System.out.println(temp[0] + "," + temp[1]);
		Board.tiles[x][y].removePiece();
		Board.tiles[z][w].addPiece(piece);
	}
	
	
	
	public boolean isNeighbor(int x,int y,int z,int w){
		if((x+1 > z || x-1 < z) && (y+1>w || y-1 < w) && (x!=z && y==w) || (x==z &&y!=w)){
			return false;
		}
		return true;
	}

	//Special rules for scout
	public boolean isStraight(int x, int y, int z, int w) throws InterruptedException{
		if((x!=z && y==w)){
			int start = x < z? x : z; 
			int end = x < z? z : x; 
			for(int i = start;start < end;start++){
				Object[] check = Board.position.queryp(new ActualField(i),new ActualField(y),new FormalField(Piece.class));
				if(check != null) {
					System.out.println("There is a piece between your start and end position");
					return false;
				}
			}
			return true;
		} else if((x==z) && (y!=w)){
			int start = y < w? y: w;
			int end = y > w? y: w;
			for(int i = start;start < end;start++){
				Object[] check = Board.position.queryp(new ActualField(x),new ActualField(i),new FormalField(Piece.class));
				if(check != null) {
					System.out.println("There is a piece between your start and end position");
					return false;
				}
			}
			return true;
		}
		System.out.println("The piece is not moving in a straight line");
		return false;
	}
}
