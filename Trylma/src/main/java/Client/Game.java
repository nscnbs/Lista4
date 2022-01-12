
package Client;

/**
 * Client game class
 */

public class Game{
    private GameConfig gameConfig;
    private Board board;
    private Frame gameFrame;
    public Game(GameConfig gameConfig, Client client) {
        this.gameConfig = gameConfig;
        board = new Board(40, gameConfig, client);
        gameFrame = new Frame(board);
    }

    public void movePawn(String player, String pawnId, String from, String to){
        board.sendPawnMoveToServer(player, pawnId, from, to);
    }


    public void setNextPlayer(String nextPlayer) {
        board.setNextPlayer(nextPlayer);
    }
}

