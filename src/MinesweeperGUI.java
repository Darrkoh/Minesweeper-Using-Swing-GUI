import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MinesweeperGUI {
    //Properties\\

    //GUI Properties
    JFrame GridLayout;
    JPanel buttPanel;
    JPanel textPanel;
    JButton[][] buttons; // 'JButton[][]' Creates a 2D array of buttons, so we can have a grid
    JLabel textLabel;


    //Other Properties
    int gridSize;
    int bombs; //Number of bombs present

    ArrayList<Integer> xPosition; //Grid's X Axis
    ArrayList<Integer> yPosition; //Grid's Y Axis
    //Methods
    public MinesweeperGUI() {
        for (int i=0; i < bombs; i++)
        {

        }
    }
}
