package dk.dtu;

import org.jspace.ActualField;
import org.jspace.FormalField;

public class SetupManager {

    public static void swap(int x, int y, int z, int w) throws InterruptedException{

		Piece firstPiece = (Piece)Board.position.get(new ActualField(x),new ActualField(y), new FormalField(Piece.class))[2];
		Piece secondPiece = (Piece)Board.position.get(new ActualField(z),new ActualField(w), new FormalField(Piece.class))[2];
		Board.position.put(x,y,firstPiece);
		Board.position.put(z,w,secondPiece);
		
		
		Board.tiles[x][y].addPiece(secondPiece);
		Board.tiles[w][z].addPiece(firstPiece);
	}


	public static void boardSetup(){
		Boolean setupInProgress = true; 
		Boolean firstClick = false;
		Boolean swapPassed = false;
		
		int pieceX = 0;
		int pieceY = 0;
		Piece piece = null;
		
		while(setupInProgress){
			try {
				firstClick = false;
				while(!firstClick){
					System.out.println("Click the first piece to switch(Or click the flag twice to end setup)");
					while(!Board.clicked){
						Thread.sleep(1);
					}
					Board.clicked = false;
					pieceX = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
					pieceY = (int) Board.currentSelectedTile.charAt(2) -'0';
					piece = (Piece) Board.position.queryp(new ActualField(pieceX), new ActualField(pieceY), new FormalField(Piece.class))[2];
					Board.currentSelectedTile = "-1,-1";
					if(piece == null || !piece.isOwner() ){
						System.out.println("Selected piece is not yours");
					}else{
						Board.tiles[pieceX][pieceY].selectTile();
						firstClick = true;
						System.out.println(pieceX + "," + pieceY + "," + piece.getJpegKey());
						System.out.println("Piece is selected");
					} 
				}
				while(!swapPassed) {
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
					if(pieceX == z && pieceY==w){
						if(piece.getPieceType() == PieceType.FLAG) {
							setupInProgress = false; //Assume finished
						}
						Board.tiles[pieceX][pieceY].deSelectTile();
						break; //Assume deselect
					}
					piece = (Piece) Board.position.queryp(new ActualField(z), new ActualField(w), new FormalField(Piece.class))[2];
					Board.currentSelectedTile = "-1,-1";
					
					if(piece == null || !piece.isOwner() ){
						System.out.println("Selected piece is not yours or is not int your area");
					}else{
						swap(pieceX,pieceY,z,w);
						Board.tiles[pieceX][pieceY].deSelectTile();
						firstClick = false;
						swapPassed = true;
					} 
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}




}
