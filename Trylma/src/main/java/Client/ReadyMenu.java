package Client;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *  Warm-up
 */

public class ReadyMenu {
    private String serverAddress;
    private Scanner in;
    private PrintWriter out;
    private JFrame frame = new JFrame("ChineseCheckers - game ready");


    //JFrame frame;
    private JPanel buttonPane, fieldsPanel;
    private JLabel cash, checks;
    private JTextField cashField, checksField;
    private JButton start, ready;
    private int AmountOfPlayers;
    private int ReadyPlayers;
    private boolean Ready;

    public void setAmountOfPlayers(){
        this.AmountOfPlayers = in.nextInt();
        System.out.println(AmountOfPlayers);
    }
    public int getReadyPlayers() {

        return ReadyPlayers;
    }


}
