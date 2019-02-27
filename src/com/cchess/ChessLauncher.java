package com.cchess;

public class ChessLauncher {
    public static void main(String[] args) {
        //ChessGame game = new ChessGame(new ChessPlayerConsole(), new ChessPlayerConsole());
        ChessGame game = new ChessGame(new ChessPlayerMiniMax(), new ChessPlayerMiniMax() );
        game.gameLoop();
    }
}