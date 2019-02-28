package com.cchess;

import java.util.List;
import java.util.ArrayList;
import com.cchess.Chess.*;


public class ChessGame {
    private final int logFrame = 10;
    final int deepAI = 5;
    public ChessPosition[] positions = new ChessPosition[deepAI+1];
    
    private ChessPlayer playerW, playerB;
    private List<Move> gameLog = new ArrayList<Move>();
    
    public ChessGame(ChessPlayer playerW, ChessPlayer playerB) {
        this.playerW=playerW;
        this.playerB=playerB;
        for(int i=0;i<positions.length;i++) positions[i]=new ChessPosition();
        positions[0].initState();
    }
    
    public ChessPlayer getCurPlayer() { return (positions[0].getCurPlayer() == Side.WHITE) ? playerW:playerB;}
    
    public void show() {
        System.out.print("\033\143");
        System.out.println();
        positions[0].show();
        System.out.println();
        System.out.println();
        int i = Math.max(0, 2*(gameLog.size()/2 -logFrame));
        for(;i<gameLog.size();i++) {
            System.out.print((i/2+1) + ". "+((i/2+1<10)? " ":""));
            System.out.print(gameLog.get(i) + "     ");
            if(i%2==1) System.out.println();
        }
        System.out.print((i/2+1) + ". "+((i/2+1<10)? " ":""));
        
    }
    
    public void gameLoop() {
        while(true) {
            show();
            Move nextMove = getCurPlayer().nextMove(this);
            if(nextMove!=null && positions[0].applyMove(nextMove)) {
                gameLog.add(nextMove);
            }
        }
    }
}