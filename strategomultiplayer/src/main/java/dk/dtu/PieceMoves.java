package dk.dtu;

public class PieceMoves {
    private int x;
    private int y;
    private int z;
    private int w;
    Move outcomeMove;
    PieceMoves(int x, int y, int z, int w,Piece piece,Move outcomeMove){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w; 
        this.outcomeMove = outcomeMove;
    }
    PieceMoves(int x, int y, int z, int w,Move outcomeMove){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w; 
        this.outcomeMove = outcomeMove;
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
    Move getOutcomeMove(){
        return outcomeMove;
    }


}
