package checkers;

public class MovementResult {
    private final MovementType type;

    public MovementType getType()
    {
        return type;
    }

    private final CheckerPieces piece;

    public CheckerPieces getPiece()
    {
        return piece;
    }

    public MovementResult(MovementType type)
    {
        this(type, null);
    }

    public MovementResult(MovementType type, CheckerPieces piece)
    {
        this.type = type;
        this.piece = piece;
    }
}
