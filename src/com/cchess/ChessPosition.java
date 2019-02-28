package com.cchess;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.cchess.Chess.*;

public class ChessPosition {
    private ChessTile[][] board = new ChessTile[8][8];
    private List<ChessFigure> figures = new ArrayList<ChessFigure>();
    private Side curPlayer = Side.WHITE;
    private double value = 0;
    
    ChessPosition() {
        for(int i=0;i<64;i++) board[i/8][i%8] = new ChessTile(i/8,i%8);
    }
    
    public void initState() {
        curPlayer=Side.WHITE;
        
        figures.add(new King(Side.WHITE).moveTo(board[0][4]));
        figures.add(new King(Side.BLACK).moveTo(board[7][4]));

        figures.add(new Queen(Side.WHITE).moveTo(board[0][3]));
        figures.add(new Queen(Side.BLACK).moveTo(board[7][3]));
        
        figures.add(new Rook(Side.WHITE).moveTo(board[0][0]));
        figures.add(new Rook(Side.WHITE).moveTo(board[0][7]));
        figures.add(new Rook(Side.BLACK).moveTo(board[7][0]));
        figures.add(new Rook(Side.BLACK).moveTo(board[7][7]));
        
        figures.add(new Bishop(Side.WHITE).moveTo(board[0][2]));
        figures.add(new Bishop(Side.WHITE).moveTo(board[0][5]));
        figures.add(new Bishop(Side.BLACK).moveTo(board[7][2]));
        figures.add(new Bishop(Side.BLACK).moveTo(board[7][5]));

        figures.add(new Knight(Side.WHITE).moveTo(board[0][1]));
        figures.add(new Knight(Side.WHITE).moveTo(board[0][6]));
        figures.add(new Knight(Side.BLACK).moveTo(board[7][1]));
        figures.add(new Knight(Side.BLACK).moveTo(board[7][6]));
        
        for(int i=0;i<8;i++) {
            figures.add(new Pawn(Side.WHITE).moveTo(board[1][i]));
            figures.add(new Pawn(Side.BLACK).moveTo(board[6][i]));
        }
        
        evaluate();
    }
    
    public ChessTile getTile(TileCoord cell) { return cell.isValid() ? board[cell.getV()][cell.getH()] : null; }
    public ChessFigure getFigure(TileCoord cell) { return cell.isValid() ? board[cell.getV()][cell.getH()].getFigure() : null; }
    public List<ChessFigure> getFigures() { return figures; }
    public Side getCurPlayer() { return curPlayer; }
    
    public boolean applyMove(Move move) {
        if(!move.start.isValid() || !move.target.isValid()) return false;
        ChessFigure figure = getFigure(move.start);
        if(figure==null || figure.getSide()!=curPlayer) return false;
        ChessFigure figureTaken = getFigure(move.target);
        if(figureTaken!=null && figureTaken.getSide()==figure.getSide()) return false;
        if(figureTaken!=null) figureTaken.moveTo(null);
        figure.moveTo(getTile(move.target));
        curPlayer=curPlayer.other();
        return evaluate();
    }
    
    public boolean evaluate() {
        for(int i=0;i<64;i++) board[i/8][i%8].init();
        value=.5;
        for(int i=0;i<figures.size();i++) {
            ChessFigure nextFigure=figures.get(i);
            if(nextFigure.getTile()==null) continue;
            value+=((getCurPlayer()==nextFigure.getSide()) ? 1:-1) * nextFigure.evaluate(this);
        }
        return true;
    }
    
    public double getValue() { return value; }
    
    public boolean validateMove(Move move) {
        ChessFigure figure = getFigure(move.start);
        return (figure!=null && figure.getSide()==getCurPlayer() && figure.canMoveTo(move.target));
    }
    
    public void copyPosition(ChessPosition origin) {
        for(int i=0;i<64;i++) board[i/8][i%8].putFigure(null);
        figures.clear();
        for(int i=0;i<origin.figures.size();i++) if(origin.figures.get(i).getTile()!=null) figures.add(origin.figures.get(i).copy(this));
        curPlayer=origin.getCurPlayer();
    }
    
    public void show() {
        System.out.print("   ");
        for(int i=0;i<8;i++) System.out.print(" "+((char)('a'+i))+"\u3000");
        System.out.println();
        for(int i=0;i<8;i++) {
            System.out.print(" "+ (8-i) +" ");
            for(int j=0;j<8;j++) System.out.print(board[7-i][j]);
            System.out.print(" "+ (8-i) +" ");
            System.out.println();
        }
        System.out.print("   ");        
        for(int i=0;i<8;i++) System.out.print(" "+((char)('a'+i))+"\u3000");
        //System.out.println(value);
    }
}