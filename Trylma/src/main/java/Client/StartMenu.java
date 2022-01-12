package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * GUI - Start Menu
 */

public class StartMenu {

    private String serverAddress;
    private Scanner in;
    private PrintWriter out;
    private JFrame frame = new JFrame("ChineseCheckers - game config");
    private JTextField textField = new JTextField(50);
    private JTextArea messageArea = new JTextArea(16, 50);


    //JFrame frame;
    private JPanel buttonPane, fieldsPanel;
    private JLabel cash, checks;
    private JTextField cashField, checksField;
    private JButton ok, cancel;
    private String name;

    /**
     * Output window asking for player's name
     */
    public StartMenu() {

        //System.out.println(this.getName());
        name = this.getName();
    }

    private String getName() {
        return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Game name selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    public String getPlayerName() {
        return name;
    }

//    public void sentPlayerName(){
//
//    }
}
