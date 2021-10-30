package checkers;

public enum PieceType {

    /* Red is given a positive 1 as its direction so that it goes down by 1 on the y-axis.
    * Black is given a negative 1 as its direction so that it goes up by 1 on the y-axis.*/
    RED(1), BLACK(-1);
    final int moveDirection;
    PieceType(int moveDirection)
    {
        this.moveDirection = moveDirection;
    }
}
