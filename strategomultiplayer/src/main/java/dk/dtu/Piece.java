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
        setPieceImage(); 
    }
    
    // Finds the jpegKey for the Specfic Piece
    private void setPieceImage(){
        if(this.pieceColor == Color.RED){
            this.jpegKey = "RED_";
            if (this.isOpponent){
                this.jpegKey += "BACK";
                return;
            }
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
                default:                                       break;
            }

        }

        else{
            this.jpegKey = "BLUE_";
            if (this.isOpponent){
                this.jpegKey += "BACK";
                return;
            }
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
}


