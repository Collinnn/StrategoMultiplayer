package dk.dtu;

import javafx.scene.paint.Color;

public class PieceMoves {
    private int x;
    private int y;
    private int z;
    private int w;
    Piece piece;
    Move outcomeMove;
    PieceMoves(int x, int y, int z, int w,Piece piece,Move outcomeMove){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w; 
        this.piece = piece;
        this.outcomeMove = outcomeMove;
    }
    PieceMoves(int x, int y, int z, int w,Move outcomeMove){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w; 
        this.piece = new Piece(PieceType.UNKOWN, Board.bottomPlayer ? Color.BLUE: Color.RED, true) ;
        this.outcomeMove = outcomeMove;
    }

    PieceMoves getPieceMoves(){
        return new PieceMoves(x,y,z,w,piece,outcomeMove);
    }
    int getX(){
        return x; 
    }
    int getY(){
        return y; 
    }
    int getZ(){
        return z;
    }
    int getW(){
        return w;
    }
    Piece getPiece(){
        return piece;
    }
    Move getOutcomeMove(){
        return outcomeMove;
    }


}
