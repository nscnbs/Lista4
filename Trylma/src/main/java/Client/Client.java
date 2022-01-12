package Client;
import org.mockito.internal.matchers.Null;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Runs the client application.
 */


public class Client implements ClientListener{
    public static void main(String[] args) {
        Client client = new Client();
        client.execute();
    }

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Game game;

    private void execute() {
        System.out.println("Hi from client. ");
        //start menu asking for players name
        StartMenu startMenu = new StartMenu();
        //ReadyMenu readyMenu = new ReadyMenu();
        GameConfig gameConfig = new GameConfig();

        /**
         *  Trying to connect to server
         */

        try {
            socket = new Socket("localhost", 59001);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Waiting for message");
            while (true) {
                String fromServer;
                String toServer;
                do {
                    fromServer = in.readLine();
                } while (fromServer == null);
                String command;
                System.out.println(fromServer);
                if (fromServer.contains(" ")) {
                    command = fromServer.substring(0, fromServer.indexOf(' '));
                } else {
                    command = fromServer;
                }
                switch (command) {
                    case "NAME_GET":
                        gameConfig.setPlayerNick(startMenu.getPlayerName());
                        out.println("NAME_SET "+startMenu.getPlayerName());
                        break;
                    case "WAITING_PLAYERS":
                        System.out.println("Waiting 4 players");
                        break;
                    case "PLAYERS_NUMBER":
                        String amountStr = fromServer.substring(15);
                        int amount = Integer.parseInt(amountStr);
                        gameConfig.setPlayersAmount(amount);
                        break;
                    case "PLAYERS_NAMES":
                        String playersNames = fromServer.substring(14);
                        ArrayList<String> names = new ArrayList<>(Arrays.asList(playersNames.split(" ")));
                        gameConfig.setNames(names);
                        break;
                    case "START_GAME":
                        gameConfig.preparePlayers();
                        game = new Game(gameConfig, this);
                        break;
                    case "MOVE":
                        String[] args = fromServer.split(" ");
                        String playerName = args[1];
                        String pawnId = args[2];
                        String fieldFrom = args[3];
                        String fieldTo = args[4];
                        String nextPlayer = args[5];
                        game.movePawn(playerName, pawnId, fieldFrom, fieldTo);
                        game.setNextPlayer(nextPlayer);
                        break;
                    case "WIN":
                        //TODO: show player winner and end game in Board
                        String[] argsW = fromServer.split(" ");
                        String playerNameW = argsW[1];
                        String pawnIdW = argsW[2];
                        String fieldFromW = argsW[3];
                        String fieldToW = argsW[4];
                        game.movePawn(playerNameW, pawnIdW, fieldFromW, fieldToW);
                        System.out.println("The game is over. The winner is"+playerNameW);
                        JOptionPane.showMessageDialog(null,"The game was won by "+playerNameW);
                        break;
                }
            }
        }

        /**
         * Exception if we couldn't find server
         */
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Not connected");
        }
        /**
         * Start game
         */
    }

    @Override
    public boolean sendMove(String message) throws IllegalArgumentException {
        out.println(message);
        return true;
    }
}