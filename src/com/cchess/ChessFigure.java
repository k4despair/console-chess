package com.cchess;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.cchess.Chess.*;

public abstract class ChessFigure {
    
    protected Side side;
    protected ChessTile tile;
    protected String avatar = "?";
    protected List<Move> moves = new ArrayList<Move>();
    
    protected class Value {
        private double value, base, move;
        public Value(double base, double move) {
            this.value=this.base=base;
            this.move=move;
        }
        public double getValue() { return value; }
        public void addMove() { value+=move; }
        public void bonusHit() { value+=move; }
        public void bonusSupport() { value+=.5*move; }
        public void clear() { value=base; }
    }
    protected Value value;

    ChessFigure(Side side) {
        this.side=side;
    }
    
    public void clear() {
        moves.clear();
        value.clear();
    }
    
    protected boolean addMoveTile(ChessTile target) {
        ChessFigure targetFigure = target.getFigure();
        target.setHit(this.side);
        value.addMove();
        if(targetFigure==null) {
            moves.add(new Move(tile.getCoord(), target.getCoord()));
            return true;
        }
        if(targetFigure.getSide() == side) value.bonusSupport();
        else {
            value.bonusHit();
            moves.add(new Move(tile.getCoord(), target.getCoord()));
        }
        return false;
    }
    
    protected void addMoveRay(ChessPosition chPosition, int dV, int dH) {
        TileCoord target=tile.getCoord().offSet(dV,dH);
        for(int i=1; target.isValid()&&addMoveTile(chPosition.getTile(target)); i++,target=tile.getCoord().offSet(i*dV,i*dH) );
    }
    
    protected void addMoveCross(ChessPosition chPosition, int dV, int dH) {
        addMoveRay(chPosition, dV, dH);
        addMoveRay(chPosition,-dV,-dH);
        addMoveRay(chPosition,-dH, dV);
        addMoveRay(chPosition, dH,-dV);        
    }
    
    public ChessFigure moveTo(ChessTile target) {
        if(tile!=null) tile.putFigure(null);
        tile=target;
        if(tile!=null) tile.putFigure(this);
        return this;
    }
    
    public Side getSide() { return side; }
    public ChessTile getTile() { return tile; }
    public double getValue() { return value.getValue(); }
    protected abstract void generateMoves(ChessPosition chPosition);

    public double evaluate(ChessPosition chPosition) {
        clear();
        generateMoves(chPosition);
        return getValue();
    }
    
    public void printMoves() {
        Iterator<Move> move = moves.iterator();
        while(move.hasNext()) System.out.println(move.next().target);
        try { Thread.sleep(5000); } catch(Exception e) {}
    }
    
    public boolean canMoveTo(TileCoord target) {
        Iterator<Move> move = moves.iterator();
        while(move.hasNext()) if(move.next().target.equals(target)) return true;
        return false;
    }
    
    public List<Move> getMoves() { return moves; }
    
    public abstract ChessFigure copy(ChessPosition chPosition);
    
    public String toString() { return avatar; }
}

class Rook extends ChessFigure{
    Rook(Side side) {
        super(side);
        avatar = (side == Side.WHITE) ? "\u001b[37m\u265C":"\u001b[34m\u265C";
        value = new Value(4, .15);
    }
    
    protected void generateMoves(ChessPosition chPosition) {
        addMoveCross(chPosition,1,0);
    }
    
    public ChessFigure copy(ChessPosition chPosition) {
        Rook res = new Rook(this.side);
        res.moveTo(chPosition.getTile(this.tile.getCoord()));
        return res;
    }
}

class Bishop extends ChessFigure{
    Bishop(Side side) {
        super(side);
        avatar = (side == Side.WHITE) ? "\u001b[37m\u265D":"\u001b[34m\u265D";
        value = new Value(2, .15);
    }
    
    protected void generateMoves(ChessPosition chPosition) {
        addMoveCross(chPosition,1,1);
    }
    
    public ChessFigure copy(ChessPosition chPosition) {
        Bishop res = new Bishop(this.side);
        res.moveTo(chPosition.getTile(this.tile.getCoord()));
        return res;
    }
}

class Queen extends ChessFigure{
    Queen(Side side) {
        super(side);
        avatar = (side == Side.WHITE) ? "\u001b[37m\u265B":"\u001b[34m\u265B";
        value = new Value(8, .15);
    }
    
    protected void generateMoves(ChessPosition chPosition) {
        addMoveCross(chPosition,1,1);
        addMoveCross(chPosition,1,0);
    }
    
    public ChessFigure copy(ChessPosition chPosition) {
        Queen res = new Queen(this.side);
        res.moveTo(chPosition.getTile(this.tile.getCoord()));
        return res;
    }
}

class King extends ChessFigure{
    King(Side side) {
        super(side);
        avatar = (side == Side.WHITE) ? "\u001b[37m\u265A":"\u001b[34m\u265A";
        value = new Value(10000, .15);
    }
    
    protected void generateMoves(ChessPosition chPosition) {
        for(int i=-1;i<2;i++)
            for(int j=-1;j<2;j++)
                if(i!=0 || j!=0)  {
                    TileCoord target=tile.getCoord().offSet(i,j);
                    if(target.isValid()) addMoveTile(chPosition.getTile(target));
               }
    }
    
    public ChessFigure copy(ChessPosition chPosition) {
        King res = new King(this.side);
        res.moveTo(chPosition.getTile(this.tile.getCoord()));
        return res;
    }
}

class Knight extends ChessFigure{
    Knight(Side side) {
        super(side);
        avatar = (side == Side.WHITE) ? "\u001b[37m\u265E":"\u001b[34m\u265E";
        value = new Value(2, .4);
    }
    
    protected void generateMoves(ChessPosition chPosition) {
        for(int i=-2;i<3;i++)
            for(int j=-1;j<2;j+=2)
                if(i!=0)  {
                    TileCoord target=tile.getCoord().offSet(i,j*(3-Math.abs(i)));
                    if(target.isValid()) addMoveTile(chPosition.getTile(target));
               }
    }
    
    public ChessFigure copy(ChessPosition chPosition) {
        Knight res = new Knight(this.side);
        res.moveTo(chPosition.getTile(this.tile.getCoord()));
        return res;
    }
}