package dk.dtu;


//Class to handle moves
public class Move {
    
    private Piece attackPiece;
    private Piece defensePiece;
    private boolean isAttack;
    private boolean attackerWins;
    private boolean defenderWins;

    public boolean isAttackMove(){
        return isAttack;
    }
    


}
