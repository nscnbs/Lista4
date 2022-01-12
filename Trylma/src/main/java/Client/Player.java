package Client;


import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ordianry player class nothing complicated some parameters setter and getter and other things
 */

public class Player {

    private ArrayList<Pawn> myPawns;
    private Color color;
    private String nick;
    private int[][] playerPawnsPositions;
    private  HashMap<String[], Color> playerWin = new HashMap<>();

    public Player() {
        this.myPawns = new ArrayList<Pawn>();
    }

    public Color getColor() {return color;}
    public void setColor(Color color) {this.color = color;}
    public String getNick() {return nick;}
    public void setNick(String nick) {this.nick = nick;}

    public ArrayList<Pawn> getMyPawns() {return myPawns;}
    public void addPawn(Pawn pawn) {this.myPawns.add(pawn);}

    public int[][] getPlayerPawnsPositions() {return this.playerPawnsPositions;}
    public void setPlayerPawnsPositions(int[][] playerPawnsPositions) {this.playerPawnsPositions = playerPawnsPositions;}

    public HashMap<String[], Color> getPlayerWin() { return this.playerWin; }
    public void setPlayerWin(HashMap<String[], Color> playerWin) { this.playerWin = playerWin; }
}
