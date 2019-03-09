package com.cchess;

public class Chess {
    public enum Side {
        WHITE, BLACK;

        public Side other() {
            return (this == WHITE) ? BLACK : WHITE;
        }
    }

}

class TileCoord {
    private int posV, posH;
    private boolean valid = false;

    TileCoord(int posV, int posH) {
        this.posV = posV;
        this.posH = posH;
        valid = ((posV >= 0) && (posV <= 7) && (posH >= 0) && (posH <= 7));
    }

    TileCoord(String pos) {
        if (pos.length() == 2 && pos.charAt(0) >= 'a' && pos.charAt(0) <= 'h' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8') {
            posH = pos.charAt(0) - 'a';
            posV = pos.charAt(1) - '1';
            valid = true;
        } else valid = false;
    }

    TileCoord(ChessTile pos) {
        if (pos != null) {
            this.posV = pos.getCoord().getV();
            this.posH = pos.getCoord().getH();
            valid = ((posV >= 0) && (posV <= 7) && (posH >= 0) && (posH <= 7));
        } else valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public int getV() {
        return posV;
    }

    public int getH() {
        return posH;
    }

    public String toString() {
        return valid ? "" + ((char) ('a' + posH)) + ((char) ('1' + posV)) : "??";
    }

    public TileCoord offSet(int offV, int offH) {
        return new TileCoord(posV + offV, posH + offH);
    }

    public boolean equals(TileCoord what) {
        return (isValid() && what.isValid() && posV == what.getV() && posH == what.getH());
    }
}

class Move {
    public TileCoord start;
    public TileCoord target;

    Move(TileCoord start, TileCoord target) {
        this.start = start;
        this.target = target;
    }

    public String toString() {
        return start + "-" + target;
    }
}

