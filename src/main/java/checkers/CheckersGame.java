package checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersGame extends Application{

    /* This makes sure that the checkerboard is 8 x 8, and it sets the size of each
    tile to 100.*/
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final Tiles[][] checkerBoard = new Tiles[WIDTH][HEIGHT];

    /* These groups are separated so that pieces can be drawn on top of certain tiles. */
    private final Group tileGroup = new Group();
    private final Group piecesGroup = new Group();

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, piecesGroup);

        /* These for loops were used to create the tiles on the checkerboard. */
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++) {
                Tiles tiles = new Tiles((x + y) % 2 == 0, x, y);
                checkerBoard[x][y] = tiles;
                tileGroup.getChildren().add(tiles);

                CheckerPieces piece = null;

                /* These if statements are used so that checker pieces can only be moved
                diagonally across the checkerboard.*/
                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = createPiece(PieceType.RED, x, y);
                }
                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = createPiece(PieceType.BLACK, x, y);
                }
                if (piece != null)
                {
                    tiles.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    private MovementResult attemptMove(CheckerPieces piece, int newX, int newY)
    {
        if (checkerBoard[newX][newY].hasPiece() || (newX + newY) % 2 == 0)
        {
            return new MovementResult(MovementType.NONE);
        }

        int x0 = toBoardCoordinates(piece.getOldX());
        int y0 = toBoardCoordinates(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDirection)
        {
            return new MovementResult(MovementType.NORMAL);
        }
        else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDirection * 2)
        {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (checkerBoard[x1][y1].hasPiece() && checkerBoard[x1][y1].getPiece().getType() != piece.getType())
            {
                return new MovementResult(MovementType.JUMP, checkerBoard[x1][y1].getPiece());
            }
        }
        return new MovementResult(MovementType.NONE);
    }

    private int toBoardCoordinates(double pixel)
    {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    /*
    public void playMusic(String musicLocation)
    {
        try
        {
            File musicPathWay = new File(musicLocation);
            if (musicPathWay.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPathWay);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else
            {
                System.out.println("Can't find file");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    */

    @Override
    public void start(Stage stage)
    {
        Scene scene = new Scene(createContent());

        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();

        /*
        // This resets the checkerboard to its original state.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.R))
            {
                Scene resetScene = new Scene(createContent());
                stage.setTitle("Checkers");
                stage.setScene(resetScene);
                stage.show();
            }
        });
        */

        // This allows a user to close out of checkers by pressing the escape button.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                stage.close();
            }
        });
    }

    private CheckerPieces createPiece(PieceType type, int x, int y)
    {
        CheckerPieces piece = new CheckerPieces(type, x, y);
        piece.setOnMouseReleased(e-> {
            int newX = toBoardCoordinates(piece.getLayoutX());
            int newY = toBoardCoordinates(piece.getLayoutY());

            MovementResult result;
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT)
            {
                result = new MovementResult(MovementType.NONE);
            }

            else
            {
                result = attemptMove(piece, newX, newY);
            }

            int x0 = toBoardCoordinates(piece.getOldX());
            int y0 = toBoardCoordinates(piece.getOldY());

            switch (result.getType()) {
                case NONE -> piece.endMove();
                case NORMAL -> {
                    piece.move(newX, newY);
                    checkerBoard[x0][y0].setPiece(null);
                    checkerBoard[newX][newY].setPiece(piece);
                }
                case JUMP -> {
                    piece.move(newX, newY);
                    checkerBoard[x0][y0].setPiece(null);
                    checkerBoard[newX][newY].setPiece(piece);
                    CheckerPieces otherPiece = result.getPiece();
                    checkerBoard[toBoardCoordinates(otherPiece.getOldX())]
                            [toBoardCoordinates(otherPiece.getOldY())].setPiece(null);
                    piecesGroup.getChildren().remove(otherPiece);
                }
            }
        });
        return piece;
    }

    public static void main(String [] args)
    { launch(args);
        /*
        String filepath = "";
        musicStuff musicObject = new musicStuff();
        musicObject.play(filepath);
        */
        System.out.println("Thanks for playing.");
    }
}
