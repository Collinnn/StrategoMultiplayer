package dk.dtu;

import javafx.scene.paint.Color;

public class Piece {
    
    private Color pieceColor;
    private PieceType pieceType;
    private boolean isOpponent;
    private String jpegKey; // Hoping to use this to get jpeg or other identfier

    public Piece(PieceType pieceType, Color pieceColor, boolean isOpponent){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.isOpponent = isOpponent;
        this.jpegKey = null;
        setPieceImage(); 
    }
    
    // Finds the jpegKey for the Specfic Piece
    private void setPieceImage(){
        if(this.pieceColor == Color.RED){
            this.jpegKey = "RED_";
            switch(pieceType){
                case FLAG: this.jpegKey += "FLAG"  ;           break;
                case BOMB: this.jpegKey += "BOMB"  ;           break;
                case SPY : this.jpegKey += "SPY"   ;           break;
                case SCOUT: this.jpegKey += "SCOUT";           break;
                case MINER: this.jpegKey += "MINER";           break;
                case SERGEANT: this.jpegKey += "SERGEANT";     break;
                case LIEUTENANT: this.jpegKey += "LIEUTENANT"; break;
                case CAPTAIN: this.jpegKey += "CAPTAIN";       break;
                case MAJOR: this.jpegKey += "MAJOR";           break;
                case COLONEL: this.jpegKey += "COLONEL";       break;
                case GENERAL: this.jpegKey += "GENERAL";       break;
                case MARSHAL: this.jpegKey += "MARSHAL";       break;
                case UNKNOWN: this.jpegKey += "BACK";		   break;
                default:                                       break;
            }

        }

        else{
            this.jpegKey = "BLUE_";
            switch(pieceType){
                case FLAG: this.jpegKey += "FLAG"  ;           break;
                case BOMB: this.jpegKey += "BOMB"  ;           break;
                case SPY : this.jpegKey += "SPY"   ;           break;
                case SCOUT: this.jpegKey += "SCOUT";           break;
                case MINER: this.jpegKey += "MINER";           break;
                case SERGEANT: this.jpegKey += "SERGEANT";     break;
                case LIEUTENANT: this.jpegKey += "LIEUTENANT"; break;
                case CAPTAIN: this.jpegKey += "CAPTAIN";       break;
                case MAJOR: this.jpegKey += "MAJOR";           break;
                case COLONEL: this.jpegKey += "COLONEL";       break;
                case GENERAL: this.jpegKey += "GENERAL";       break;
                case MARSHAL: this.jpegKey += "MARSHAL";       break;
                case UNKNOWN: this.jpegKey += "BACK";		   break;
                default:                                       break;
            }

        }
    }

    public PieceType getPieceType(){
        return pieceType;
    }

    public Color getPieceColor(){
        return pieceColor;
    }
    
    public boolean isOwner() {
    	return !this.isOpponent;
    }

    public String getJpegKey(){
        return jpegKey;
    }
    public String toString(){
        return this.jpegKey;
    }
    public void setPieceType(PieceType type) {
    	this.pieceType = type;
    	System.out.println(type);
    	this.jpegKey = this.jpegKey.split("[_]")[0] + "_";
    	switch(type){
	        case FLAG: this.jpegKey += "FLAG"  ;           break;
	        case BOMB: this.jpegKey += "BOMB"  ;           break;
	        case SPY : this.jpegKey += "SPY"   ;           break;
	        case SCOUT: this.jpegKey += "SCOUT";           break;
	        case MINER: this.jpegKey += "MINER";           break;
	        case SERGEANT: this.jpegKey += "SERGEANT";     break;
	        case LIEUTENANT: this.jpegKey += "LIEUTENANT"; break;
	        case CAPTAIN: this.jpegKey += "CAPTAIN";       break;
	        case MAJOR: this.jpegKey += "MAJOR";           break;
	        case COLONEL: this.jpegKey += "COLONEL";       break;
	        case GENERAL: this.jpegKey += "GENERAL";       break;
	        case MARSHAL: this.jpegKey += "MARSHAL";       break;
	        default:                                       break;
    	}
    	System.out.println(jpegKey);
    }
}


