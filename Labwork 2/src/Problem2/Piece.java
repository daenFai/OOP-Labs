package Problem2;

public abstract class Piece {
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean isLegalMove(Position secondPosition);
}
