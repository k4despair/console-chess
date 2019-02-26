package com.cchess;

import java.util.Scanner;

public interface ChessPlayer {
    Move nextMove(ChessGame game);
}

class ChessPlayerConsole implements ChessPlayer {
    public Move nextMove(ChessGame game) {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        if(input.length()==5 && input.charAt(2)=='-') {
            TileCoord start = new TileCoord(input.substring(0,2));
            TileCoord target = new TileCoord(input.substring(3,5));
            Move move = new Move(start, target);
            if(start.isValid() && target.isValid() && game.positions[0].validateMove(move)) return move;
        }
        return null;
    }
}

class ChessPlayerMiniMax implements ChessPlayer {
    private Move best;
    
    public Move nextMove(ChessGame game) {
        best = null;
        evaluate(game, 0, Double.POSITIVE_INFINITY);
        return best;
    }
    
    private double evaluate(ChessGame game, int depth, double clamp) {
        double res=Double.NEGATIVE_INFINITY;
        for(int i=0;i<game.positions[depth].getFigures().size();i++) {
            ChessFigure figure=game.positions[depth].getFigures().get(i);
            if(figure.getTile()==null || figure.getSide()!=game.positions[depth].getCurPlayer()) continue;
            for(int j=0;j<figure.getMoves().size();j++) {
                Move move=figure.getMoves().get(j);
                game.positions[depth+1].copyPosition(game.positions[depth]);
                if(game.positions[depth+1].applyMove(move)) {
                    double tmp=Double.NEGATIVE_INFINITY;
                    if(depth+2==game.positions.length) tmp=-game.positions[depth+1].getValue();
                    else tmp=-evaluate(game,depth+1,clamp);
                    if(tmp>res) {
                        res=tmp;
                        if(depth==0) best=move;
                    }
                }
            }
        }
            
        return res;
    }
}