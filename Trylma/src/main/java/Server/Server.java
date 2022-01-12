package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;


import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Runs the server application.
 */


public class Server implements ServerListener {

    private int playerNumber;
    private GameRules gameRules;
    private Game game;
    private final Object lock = new Object();
    BlockingQueue<String> queue;

    String fromClient;

    private static SharedData data;

    public Server() {
        data = SharedData.getInstance();
        queue = new LinkedBlockingQueue<>();
    }

    public void broadcast(String message) {
        Collection<PlayerHandler> players = data.getPlayerHandlers();
        for (PlayerHandler playerHandler : players) {
            playerHandler.sendStr(message);
        }
    }

    private void getNewConfig() {
        playerNumber = UserInterface.getInt("Podaj liczbe graczy.");
        gameRules = new GameRules();
    }

    private void sendPlayersNames() {
        StringBuilder stringBuilder = new StringBuilder("PLAYERS_NAMES");
        Collection<String> playersName = data.getNames();
        for(String name : playersName) {
            stringBuilder.append(" ");
            stringBuilder.append(name);
        }
        System.out.println(stringBuilder.toString());
        broadcast(stringBuilder.toString());
    }

    private void resetConnection() {
        Collection<PlayerHandler> players = data.getPlayerHandlers();
        for (PlayerHandler playerHandler : players) {
            playerHandler.close();
        }
        players.clear();
    }

    /**
     *  Start server
     */
    public void start() {
        UserInterface.print("Starting server");
        while (true) {
            UserInterface.print("Starting game");

            getNewConfig();
            data.setNumberOfPlayers(playerNumber);

            // Wait for players
            UserInterface.print("Waiting for players");
            broadcast("WAITING_PLAYERS");
            int currentPlayerNumber = 0;

            Executor pool = Executors.newFixedThreadPool(6);

            try (ServerSocket listener = new ServerSocket(59001)) {
                System.out.println("Connected players : " + currentPlayerNumber);
                while (currentPlayerNumber < playerNumber) {

                    PlayerSocket playerSocket = new PlayerSocket(listener.accept());
                    PlayerHandler playerHandler = new PlayerHandler(playerSocket, data, this);
                    data.addPlayerHandlers(playerHandler);
                    pool.execute(playerHandler);
                    currentPlayerNumber++;

                    System.out.println("Player connected :)");
                }
            } catch (IOException e) {

            }

            while(data.getNames().size() != data.getNumberOfPlayers()) {}

            sendPlayersNames();
            ArrayList<String> playersNames = new ArrayList<>(data.getNames());
            game = new Game(gameRules, playersNames);

            broadcast("START_GAME");

            Iterator<String> playerNames = data.getNames().iterator();

            while (true) {
                try {
                    String message = queue.take();
                    String command = message.substring(0, message.indexOf(" "));
                    if(command.equals("MOVE")) {
                        if(game.acceptMove(message)) {
                            message += " ";
                            message += game.getCurrentPlayer();
                            broadcast(message);
                        }
                    } else if(command.equals("WIN")) {
                        if(game.ended(message)) {
                            broadcast(message);
                            resetConnection();
                            break;
                        }
                    }

                } catch (InterruptedException e) {}
            }



            /*while (data.game.ended()) {
                if (!playerNames.hasNext())
                    playerNames = data.getNames().iterator();

                broadcast("MOVE_NOW " + playerNames.next());
                try {
                    pool.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    /**
     * Run server
     */

    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }

    @Override
    public void notifyServer(String message) {
        queue.add(message);
    }

    @Override
    public void notifyNameSet() {
    }
}
