package com.cchess;

import com.cchess.Chess.*;

public class ChessTile {
    
    protected ChessFigure figure;
    protected TileCoord position;
    protected boolean hitW=false;
    protected boolean hitB=false;
    
    ChessTile(int posV, int posH) {
        position = new TileCoord(posV,posH); 
    }
    
    public ChessFigure getFigure() {return figure;}
    public TileCoord getCoord() {return position;}
    public void putFigure(ChessFigure figure) { this.figure=figure; }
    public String toString() {
        String res = (position.getH()+position.getV())%2 == 0 ? "\u001b[40m\u001b[30m ":"\u001b[43m\u001b[33m ";
        res = res + (figure==null? "\u3000":figure);
        res+=" \u001b[0m";
        return res;
    }
    public boolean isHit(Side side) { return (side==Side.WHITE)? hitW:hitB; }
    public void init() { hitB=hitW=false; }
    public void setHit(Side side) { if(side==Side.WHITE) hitW=true; else hitB=true;}
    
}