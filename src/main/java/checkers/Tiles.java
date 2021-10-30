package checkers;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tiles extends Rectangle
{
    private CheckerPieces piece;

    public boolean hasPiece()
    {
        return piece != null;
    }

    public CheckerPieces getPiece()
    {
        return piece;
    }

    public void setPiece(CheckerPieces piece)
    {
        this.piece = piece;
    }

    public Tiles(boolean light, int x, int y)
    {
        setWidth(CheckersGame.TILE_SIZE);
        setHeight(CheckersGame.TILE_SIZE);

        relocate(x * CheckersGame.TILE_SIZE, y * CheckersGame.TILE_SIZE);

        /* These hexadecimal values were used to create a board that uses the colors
        wheat and sienna. I decided to use these colors because red and black squares
         did not contrast well with red and black pieces. */
        setFill(light ? Color.valueOf("#F5DEB3") : Color.valueOf("#A0522D"));
    }
}
