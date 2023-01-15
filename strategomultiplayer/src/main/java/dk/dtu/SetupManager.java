package dk.dtu;

public class SetupManager implements Runnable {

    @Override
    public void run() {
        boardSetup();
        
    }

    public void swap(int x, int y, int z, int w){

		Piece firstPiece = Board.position.get(new ActualField(x),new ActualField(y), new FormalField(Piece.class));
		Piece secondPiece = Board.position.get(new ActualField(z),new ActualField(w), new FormalField(Piece.class));
		board.position.put(x,y,firstPiece);
		board.position.put(z,w,secondPiece);

		Board.tiles[x][y].removePiece();
		Board.tiles[w][z].removePiece();
		Board.tiles[x][y].addPiece(secondPiece);
		Board.tiles[w][z].addPiece(firstPiece);
	}


	public void boardSetup(){
		Boolean setupInProgress = true; 
		Boolean firstClick,movePassed = false;
		
		Tile firsttile = null;
		while(setupInProgress){
			while(!firstClick){
				System.out.println("Click the first piece to switch");
				while(!Board.Clicked){
					Thread.sleep(1);
				}
				Board.clicked = false;
				if(checkString(Board.currentSelectedTile)){
					pieceX = (int) Board.currentSelectedTile.charAt(0) -'0'; //Forces it to be an string
					pieceY = (int) Board.currentSelectedTile.charAt(2) -'0';
					piece = queryPiece(pieceX, pieceY);
					System.out.println(piece.getPieceType().getValue());
					Board.currentSelectedTile = "-1,-1";
					if(piece == null || !piece.isOwner() ){
						System.out.println("Selected piece is not yours");
					}else{
						Board.tiles[pieceX][pieceY].selectTile();
						piecePassed = true;
						System.out.println(pieceX + "," + pieceY + "," + piece.getJpegKey());
						System.out.println("Piece is selected");
						break;
					} 
				}

			}
			while(!movePassed) {
					//Z and W are the coordinates where the piece will move to 
					int z = -1;
					int w = -1;
					while(true){
						System.out.println("Click tile to move to (If you click the same tile it unselects)");
						while(!Board.clicked){
							Thread.sleep(1);
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
						Board.tiles[pieceX][pieceY].deSelectTile();
						piecePassed = false;
						break; //Assume deselect
					}
					swap(pieceX,pieceY,z,w);
					break; //Returns to firstclick loop to ready another action
				}
		}
	}




}
