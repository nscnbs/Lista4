package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;

import javax.swing.JPanel;

/**
 * Game board
 */

public class Board extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private int[] fieldArray;
    private int size;
    private ArrayList<Pawn> pawns;
    private ArrayList<Field> fields;
    private ArrayList<Field> possibleFields;
    public static ArrayList<Player> players;
    private Pawn activePawn;
    private Player whoseTurn;
    private ClientListener clientListener;
    private Player player;
    private HashMap<Color,String[]> winMap;
    public int countPawns = 0;

    /**
     * Initalizing board
     */
    public Board(int size, GameConfig gameConfig, ClientListener clientListener) {
        this.size = size;
        this.fieldArray = gameConfig.fieldArray;
        this.players = new ArrayList<>();
        for(Player player: gameConfig.players) {
            this.players.add(player);
        }
        this.player = findPlayerByNick(gameConfig.getPlayerNick());
        this.whoseTurn = players.get(0);
        this.setFields();
        this.setPawns();
        this.clientListener = clientListener;
        this.winMap = gameConfig.getWinMap();
        addMouseListener(this);
    }

    private Player findPlayerByNick(String nick) {
        for(Player player : players) {
            if(player.getNick().equals(nick)) {
                return player;
            }
        }
        return null;
    }

    /**
    *  Colorizing
    */

    public void paintComponent(Graphics g) {
        this.paintFields(g);
        if (this.activePawn != null) {
            this.setPossibleFields(g);
            this.paintPossibleFields(g);
        }
        this.paintPawns(g);
        this.printPlayers(g);
    }

    private void paintPossibleFields(Graphics g) {
        for(Field field : this.possibleFields) {
            g.setColor(Color.GRAY);
            g.fillOval(field.getxPosition(), field.getyPosition(), field.getSize(), field.getSize());
        }

    }


    /**
     *  Set pawns color according to players color
     */
    private void paintPawns(Graphics g) {
        for (Player player: this.players) {
            for (Pawn pawn: player.getMyPawns()) {
                g.setColor(pawn.getColor());
                g.fillOval(pawn.getX(), pawn.getY(), pawn.getSize(), pawn.getSize());
            }
        }
    }

    private void updateCountPawns(Field field) {
        //TODO: list/collection for checking of existence of already checked id
        String[] idTab = this.winMap.get(player.getColor());
        List<String> idList = Arrays.asList(idTab);
        if(idList.contains(field.getId())) {
            countPawns ++;
            System.out.println("CountPawns:"+countPawns);
        }
    }

    public boolean ifPlayerWins() {
        if(countPawns == 1) {
            return true;
        }
        return false;
    }

    /**
     *  Printing players list
     */
    private void printPlayers(Graphics g) {
        int x = 750;
        int y = 40;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x - this.size/2, this.size/2, 100, 200);

        for (Player player: this.players) {
            if (player.equals(this.whoseTurn)) {
                g.setFont(new Font("default", Font.BOLD, 12));
            }
            g.setColor(Color.black);
            g.drawString(player.getNick(), x, y);
            g.setFont(new Font("default", Font.PLAIN, 12));
            y += (this.size/2);
        }
    }

    private void paintFields(Graphics g) {
        for (Field field: this.fields) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillOval(field.getxPosition(), field.getyPosition(), field.getSize(), field.getSize());
        }
    }

    /**
     *  Mapping field
     */
    private void setFields() {
        this.fields = new ArrayList<Field>();
        int xPosition = 400;
        int yPosition = 20;
        int pawnPosition = 0;
        int id = 0;
        for (int i: this.fieldArray) {
            xPosition = xPosition - (i*this.size / 2);
            for (int j = 0; j < i; j++) {
                this.fields.add(new Field(xPosition, yPosition, this.size, Integer.toString(id)));
                xPosition += this.size;
                pawnPosition++;
                id++;
            }
            yPosition += this.size;
            xPosition = 400;
        }
    }

    /**
     * Mapping pawns on the field
     */
    private void setPawns() {
        this.pawns = new ArrayList<>();
        for (Player player: this.players) {
            int xPosition = 400;
            int yPosition = 20;
            int row = 0;
            for (int i: this.fieldArray) {
                xPosition = xPosition - (i*this.size / 2);
                for(int j = 0; j < i; j++) {
                    //System.out.println("row: " + row + "; j: " + j);
                    int[] playerPawnsPositions = player.getPlayerPawnsPositions()[row];
                    if(playerPawnsPositions.length != 0) {	// wchodzimy jeżeli player ma w tym wierszu jakieś pionki
                        for (int k: playerPawnsPositions) {
                            if (k == j) {
                                Pawn newPawn = new Pawn(this.size);
                                newPawn.setColor(player.getColor());
                                newPawn.setPosition(xPosition, yPosition);
                                newPawn.setPlayerNick(player.getNick());
                                player.addPawn(newPawn);
                                this.pawns.add(newPawn);
                            }
                        }
                    }
                    xPosition += this.size;
                }
                xPosition = 400;
                yPosition += this.size;
                row++;
            }
        }
    }

    /**
     * Can you move pawn to this position
     */
    private boolean canPawnMoveThere(Pawn pawn, Field field) {
        int fieldX = field.getxPosition();
        int fieldY = field.getyPosition();
        int pawnX = pawn.getX();
        int pawnY = pawn.getY();
        if ((Math.abs(fieldX - pawnX) == this.size &&
                Math.abs(fieldY - pawnY) == 0) ||
                (Math.abs(fieldX - pawnX) == (this.size/2) &&
                        Math.abs(fieldY - pawnY) == this.size)) {
            return true;
        }
        return false;
    }

    private boolean canPawnPossiblyMoveThere(Field potentialField, Field field) {
        int fieldX = field.getxPosition();
        int fieldY = field.getyPosition();
        int potentialFieldX = potentialField.getxPosition();
        int potentialFieldY = potentialField.getyPosition();
        if ((Math.abs(fieldX - potentialFieldX) == this.size &&
                Math.abs(fieldY - potentialFieldY) == 0) ||
                (Math.abs(fieldX - potentialFieldX) == (this.size/2) &&
                        Math.abs(fieldY - potentialFieldY) == this.size)) {
            return true;
        }
        return false;
    }

    /**
     * Is field empty or not
     */

    private boolean isFieldOccupied(Field field) {
        for (Pawn anyPawn: this.pawns) {
            if (anyPawn.getX() == field.getxPosition() && anyPawn.getY() == field.getyPosition()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hooping over pawns
     */
    private boolean canPawnHopThere(Pawn pawn, Field jumpOverField, Field destinationField) {
        int innerDistanceX = (jumpOverField.getxPosition() - this.activePawn.getX());
        int innerDistanceY = (jumpOverField.getyPosition() - this.activePawn.getY());
        int fieldsDistanceX = (destinationField.getxPosition() - jumpOverField.getxPosition());
        int fieldsDistanceY = (destinationField.getyPosition() - jumpOverField.getyPosition());
        if (innerDistanceX == fieldsDistanceX && innerDistanceY == fieldsDistanceY) {
            if(!this.isFieldOccupied(destinationField)) {
                return true;
            }
        }
        return false;
    }

    private boolean canPawnPotentiallyHopThere(Field potentialField, Field jumpOverField, Field destinationField) {
        int innerDistanceX = (jumpOverField.getxPosition() - potentialField.getxPosition());
        int innerDistanceY = (jumpOverField.getyPosition() - potentialField.getyPosition());
        int fieldsDistanceX = (destinationField.getxPosition() - jumpOverField.getxPosition());
        int fieldsDistanceY = (destinationField.getyPosition() - jumpOverField.getyPosition());
        if (innerDistanceX == fieldsDistanceX && innerDistanceY == fieldsDistanceY) {
            if(!this.isFieldOccupied(destinationField)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Painting possible moves
     */
    private void setPossibleFields(Graphics g) {
        this.possibleFields = new ArrayList<Field>();
        ArrayList<Field> potentialFieldToHopFrom = new ArrayList<>();
        for (Field field: this.fields) {
            if(this.canPawnMoveThere(this.activePawn, field)) {
                if (!this.isFieldOccupied(field)) {
                    this.possibleFields.add(field);
                } else {
                    for (Field innerField: this.fields) {
                        if(this.canPawnHopThere(this.activePawn, field, innerField)) {
                            potentialFieldToHopFrom.add(innerField);
                        }
                    }
                }
            }
        }

        while(potentialFieldToHopFrom.size() != 0) {
            ArrayList<Field> checkedFields = new ArrayList<>(potentialFieldToHopFrom);
            potentialFieldToHopFrom = new ArrayList<>();
            this.possibleFields.addAll(checkedFields);
            for (Field potentialField: checkedFields) {
                for (Field field: this.fields) {
                    if(this.canPawnPossiblyMoveThere(potentialField, field) && this.isFieldOccupied(field)) {
                        for (Field innerField : this.fields) {
                            if (this.canPawnPotentiallyHopThere(potentialField, field, innerField)) {
                                if (!this.possibleFields.contains(innerField)) {
                                    potentialFieldToHopFrom.add(innerField);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void sendPawnMoveToServer(String player, String pawnId, String from, String to) {
        Field field = findFieldById(to);
        Pawn pawn = findPawnById(pawnId);

        movePawnFromExternal(pawn, field);
    }

    private Field findFieldById(String id) {
        for(Field field : fields) {
            if(field.getId().equals(id))
                return field;
        }
        return null;
    }

    private Pawn findPawnById(String id) {
        for(Pawn pawn : pawns) {
            if(pawn.getId().equals(id))
                return pawn;
        }
        return null;
    }

    /**
     * Pawn moving
     */
    private void movePawnFromExternal(Pawn pawn, Field field) {
        pawn.setPosition(field.getxPosition(), field.getyPosition());
        activePawn = null;
        this.repaint();
    }

    private void sendPawnMoveToServer(Pawn pawn, Field field) {

        if(this.possibleFields.contains(field)) {
            Field fromField = field;
            for(Field pawnField: this.fields) {
                if (pawn.getX() == pawnField.getxPosition() && pawn.getY() == pawnField.getyPosition()) {
                    fromField = pawnField;
                }
            }
            if (fromField != null) {
                System.out.println(pawn.getPlayerNick() + ": MOVE FROM " + fromField.getId() + " TO " + field.getId() );
                updateCountPawns(field);
                if(ifPlayerWins()) {
                    clientListener.sendMove("WIN " + pawn.getPlayerNick() + " " + activePawn.getId() + " " + fromField.getId() + " " + field.getId());
                } else {
                    try {
                        clientListener.sendMove("MOVE " + pawn.getPlayerNick() + " " + activePawn.getId() + " " + fromField.getId() + " " + field.getId());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }
        else {
            System.out.println("error, wrong move");
        }
    }


    /**
     * Mouse event listener
     */
    public void mouseClicked(MouseEvent arg0) {
        boolean pawnFound = false;

        for (Field field: this.fields) {
            if ((new Ellipse2D.Float(field.getxPosition(), field.getyPosition(), field.getSize(), field.getSize())).contains(arg0.getPoint())) {
                System.out.println(field.getId());
                break;
            }
        }

        for (Pawn pawn: this.pawns) {
            if (pawn != null) {
                if ((new Ellipse2D.Float(pawn.getX(), pawn.getY(), pawn.getSize(), pawn.getSize())).contains(arg0.getPoint())) {
                    pawnFound = true;
                    if(pawn.getPlayerNick().equals(player.getNick()) && pawn.getPlayerNick().equals(whoseTurn.getNick())) {
                        this.activePawn = pawn;
                        this.repaint();
                    }
                }
            }
        }
        if (!pawnFound) {
            for (Field field: this.fields) {
                if ((new Ellipse2D.Float(field.getxPosition(), field.getyPosition(), field.getSize(), field.getSize())).contains(arg0.getPoint())) {
                    if (this.activePawn != null) {
                        //System.out.println((field.getBounds().x - this.activePawn.getShape().getBounds().x) + "  " + (field.getBounds().y - this.activePawn.getShape().getBounds().y));
                        this.sendPawnMoveToServer(this.activePawn, field);
                        //System.out.println("ActivePawn change");
                    }
                    //System.out.println("field: x: " + arg0.getX() + "; y: " + arg0.getY());
                }
                //this.repaint();
            }
        }


    }

    public void mouseEntered(MouseEvent arg0) {	}

    public void mouseExited(MouseEvent arg0) { }

    public void mousePressed(MouseEvent arg0) {	}

    public void mouseReleased(MouseEvent arg0) { }

    public void mouseDragged(MouseEvent arg0) {	}

    public void mouseMoved(MouseEvent arg0) { }

    public void actionPerformed(ActionEvent arg0) {	}

    public void setNextPlayer(String nextPlayerNick) {
        whoseTurn = findPlayerByNick(nextPlayerNick);
    }
}
