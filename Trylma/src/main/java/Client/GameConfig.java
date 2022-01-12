package Client;

import Server.PlayerSocket;
import Server.Server;

import java.awt.Color;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Configuration of game
 */
public class GameConfig {
    public int[] fieldArray = {1,2,3,4,13,12,11,10,9,10,11,12,13,4,3,2,1};
    public ArrayList<Shape> fields = new ArrayList<Shape>();
    public ArrayList<Player> players = new ArrayList<Player>();
    private int amount;
    private ArrayList<String> names;
    private String playerNick;
    
    private HashMap<Color,String[]> winMap = new HashMap<>();

    public GameConfig() {
    }

    public void setPlayersAmount(int amount) {
        this.amount = amount;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public String getPlayerNick() {
        return playerNick;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public HashMap<Color, String[]> getWinMap() {
        return winMap;
    }

    public void preparePlayers() {
        while(names.size() < 6) {
            names.add(" ");
        }
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();
        Player player6 = new Player();
        player1.setColor(Color.blue);
        player2.setColor(Color.red);
        player3.setColor(Color.green);
        player4.setColor(Color.magenta);
        player5.setColor(Color.orange);
        player6.setColor(Color.pink);
        player1.setNick(names.get(0));
        player2.setNick(names.get(1));
        player3.setNick(names.get(2));
        player4.setNick(names.get(3));
        player5.setNick(names.get(4));
        player6.setNick(names.get(5));
        //presets for different amount of players
        switch (amount) {
            case 2:
                System.out.println("Setting board for 2 players");
                player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}});
                
                String[] player1Win = {"111","112","113","114","115","116","117","118","119","120"};
                String[] player2Win = {"0","1","2","3","4","5","6","7","8","9"};
                winMap = new HashMap<>();
                winMap.put(Color.blue,player1Win);
                winMap.put(Color.red,player2Win);

                this.players.add(player1);
                this.players.add(player2);
                break;
            case 3:
                System.out.println("Setting board for 3 players");
                player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
                player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
                this.players.add(player1);
                this.players.add(player2);
                this.players.add(player3);
                break;
            case 4:
                System.out.println("Setting board for 4 players");
                player1.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {9,10,11,12}, {9,10,11}, {9,10}, {9}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
                player4.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
                this.players.add(player1);
                this.players.add(player2);
                this.players.add(player3);
                this.players.add(player4);
                break;
            case 6:
                System.out.println("Setting board for 6 players");
                player1.setPlayerPawnsPositions(new int[][] {{0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player2.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player3.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {9,10,11,12}, {9,10,11}, {9,10}, {9}, {}, {}, {}, {}, {}, {}, {}, {}, {}});
                player4.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {0}, {0,1}, {0,1,2}, {0,1,2,3}, {}, {}, {}, {}});
                player5.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {9}, {9,10}, {9,10,11}, {9,10,11,12}, {}, {}, {}, {}});
                player6.setPlayerPawnsPositions(new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {0,1,2,3}, {0,1,2}, {0,1}, {0}});

                this.players.add(player1);
                this.players.add(player2);
                this.players.add(player3);
                this.players.add(player4);
                this.players.add(player5);
                this.players.add(player6);
                break;

        }
    }
}
