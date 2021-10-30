package checkers;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static checkers.CheckersGame.TILE_SIZE;

public class CheckerPieces extends StackPane
{
    private final PieceType type;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public PieceType getType()
    {
        return type;
    }

    public double getOldX()
    {
        return oldX;
    }

    public double getOldY()
    {
        return oldY;
    }

    public CheckerPieces(PieceType type, int x, int y)
    {
        this.type = type;
        move(x, y);

        // This is used to create the appearance of the red and black checker pieces.
        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED ? Color.valueOf("black") : Color.valueOf("white"));
        ellipse.setStroke(Color.RED);
        ellipse.setStrokeWidth(TILE_SIZE * .003);

        // This centers each checker piece on a tile.
        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse2 = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse2.setFill(type == PieceType.BLACK ? Color.valueOf("black") : Color.valueOf("red"));
        ellipse2.setStroke(Color.BLACK);
        ellipse2.setStrokeWidth(TILE_SIZE * .003);

        // This centers each checker piece on a tile.
        ellipse2.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse2.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(ellipse, ellipse2);

        setOnMousePressed(e-> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMouseDragged(e-> relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));
    }
    public void move(int x, int y)
    {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void endMove()
    {
        relocate(oldX, oldY);
    }
}
