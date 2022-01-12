package Server;

import java.util.ArrayList;

/**
 * Server game class
 */

public class Game {

    private GameRules rules;
    private int currentPlayer;
    private ArrayList<String> playersNames;

    public Game(GameRules rules, ArrayList<String> playersNames) {
        this.rules = rules;
        this.playersNames = playersNames;
        currentPlayer = 0;
    }

    private void setNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= playersNames.size()) {
            currentPlayer = 0;
        }
    }

    public String getCurrentPlayer() {
        return playersNames.get(currentPlayer);
    }

    public boolean acceptMove(String str) {
        String playerNick = str.split(" ")[1];
        if(playersNames.get(currentPlayer).equals(playerNick)) {
            setNextPlayer();
            return true;
        }
        return false;
    }
    public boolean ended(String str) {
        String playerNick = str.split(" ")[1];
        if(playersNames.get(currentPlayer).equals(playerNick)) {
            setNextPlayer();
            return true;
        }
        return false;
    }
}
