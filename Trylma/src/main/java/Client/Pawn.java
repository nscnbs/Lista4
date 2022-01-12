package Client;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *  Pawn class
 */

public class Pawn {
    private Color color;
    private int xPosition;
    private int yPosition;
    private String playerNick;
    private String id;
    private int size;

    /**
     * Asking player nickname
     */
    public String getPlayerNick() {
        return playerNick;
    }

    /**
     * Write players nickname into object
     */
    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public Pawn(int size) {
        this.id = IdGenerator.getNext();
        this.size = size;
    }

    /**
     * Set position of player
     */
    public void setPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Returning position
     */
    public int getX() {
        return this.xPosition;
    }
    public int getY() {
        return this.yPosition;
    }

    /**
     * Set players color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Get id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set id
     */
    public void setId(String id) {
        this.id = id;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    static class IdGenerator {
        static int id = 0;
        static String getNext() {
            return String.valueOf(id++);
        }
    }
}
