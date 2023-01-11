package dk.dtu;

public enum PieceType { //ENUM IS STATIC VARIABLES
    FLAG(1,-1),
    BOMB(4,-1),
    SPY(1,-1),
    SCOUT(8,2),
    MINER(5,3),
    SERGEANT(4,4),
    LIEUTENANT(4,5),
    CAPTAIN(4,6),
    MAJOR(3,7),
    COLONEL(2,8),
    GENERAL(1,9),
    MARSHAL(1,10);


    private int count;
    private int value;


    PieceType(int count, int value){
        this.count = count; 
        this.value = value;
    }
    public int getCount(){
        return count;
    }
    //Probaly change this to work as a battle outcome with a PieceType from both accepted, might only need one since the other is this PIECE
    public int getValue(){ 
        return value;
    }



}
