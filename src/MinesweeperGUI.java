import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class MinesweeperGUI implements ActionListener {
    //Properties\\

    //GUI Properties
    JFrame frame;
    JPanel GridLayout;
    JPanel buttPanel;
    JPanel textPanel;
    JButton[][] buttons; // 'JButton[][]' Creates a 2D array of buttons, so we can have a grid
    JLabel textLabel;
    int [][] solution; //2D Array which will check each button and add the number of bombs around each button to a variable


    //Other Properties
    int gridSize;
    int bombs; //Number of bombs present

    Random random; //Import Random class for the placement of bombs
    ArrayList<Integer> xPosition; //Grid's X Axis
    ArrayList<Integer> yPosition; //Grid's Y Axis
    //Methods
    public MinesweeperGUI() {
        xPosition = new ArrayList<>();
        yPosition = new ArrayList<>(); //Instantiates Arraylists for coordinates

        gridSize = 9;
        bombs = 3;
        random = new Random(); //Instantiate the random variable

        //Frame Setup\\
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Will Close application on exit
        frame.pack(); //Will fit window to size of contents
        frame.setVisible(true); //So we can see it
        frame.setLayout(new BorderLayout()); //We want a border layout

        //Text Panel Setup\\
        textPanel = new JPanel();
        textPanel.setVisible(true);
        textPanel.setBackground(Color.DARK_GRAY);

        //Button Panel Setup\\
        buttPanel = new JPanel();
        buttPanel.setVisible(true);
        buttPanel.setLayout(new GridLayout(gridSize, gridSize)); //Creates a Grid according to the size given by the gridSize Parameter

        //Text Label Setup\\
        textLabel = new JLabel();
        textLabel.setHorizontalAlignment(JLabel.CENTER); //Text of button when pressed, e.g. will show how many bombs are next to button.
        textLabel.setFont(new Font("MV Bold", Font.BOLD, 20));
        textLabel.setForeground(Color.WHITE);
        textLabel.setText("There are " + bombs + " Bombs");

        solution = new int[gridSize][gridSize]; //Array Size is the size of the grid

        //Buttons Setup\\
        buttons = new JButton[gridSize][gridSize];
        for (int i=0; i<buttons.length; i++)
        {
            for(int j=0; j<buttons.length; j++) //This will allow us to go through every button in the array, going through each column of a row before moving onto the next row
            {
                buttons[i][j] = new JButton(); //Instantiates a button for each array position
                buttons[i][j].setFocusable(false); //Cannot be navigated to via keyboard
                buttons[i][j].setFont(new Font("MV Bold", Font.BOLD, 12));
                buttons[i][j].setText(" ");
                buttons[i][j].addActionListener(this); //Will trigger the actionPerformed method when a button is pressed
                buttPanel.add(buttons[i][j]); //Adds Button to the panel
            }
        }

        //Combining elements
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(buttPanel);

        frame.setSize(570,570);
        frame.revalidate(); //Refreshes GUI so that all elements of the GUI have their assigned properties
        frame.setLocationRelativeTo(null); //Frame will be in center of the screen

        //////////////////////////////DETERMINE BOMB COORDINATES\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        for (int i=0; i < bombs; i++) {
            xPosition.add(random.nextInt(gridSize)); //Uses the random class to determine a bombs position on the grid's x-axis e.g a 9x9 grid gives a number between 0-9
            yPosition.add(random.nextInt(gridSize)); //Same thing but Y Axis
        }

        //To Prevent Overlapping of bombs (Needs to be checked as soon as possible)
        for (int i=0; i < bombs; i++) //First Bomb Location
        {
            for (int j=i+1; i < bombs; i++) //Another Bomb's Location
            {
                if (xPosition.get(i).equals(xPosition.get(j)) && yPosition.get(i).equals(yPosition.get(j))) //If the positions of the 2 bombs are exactly the same
                {
                    xPosition.set(j, random.nextInt(gridSize));
                    yPosition.set(j, random.nextInt(gridSize)); //Gives the secondary bomb a new X and Y Value

                    i=0;
                    j=0; //Resetting counters
                }
            }
        }

        //GET RID OF AT END, THIS IS TO CHECK COORDINATES

        for (int i=0; i < bombs; i++) {
            System.out.println("The X axis of Bomb " + i + " is " + xPosition.get(i) + " and the Y axis is " + yPosition.get(i));
        }
        getSolution(); //Will get the solution of each tile
    }



            //////////For Bombs Around Tile Clicked\\\\\\\\\\
    public void getSolution() {
        for (int y = 0; y < solution.length; y++)
        {
            for (int x = 0; x < solution[0].length; x++) //Goes through 9x9 arraylist of solution variable
            {
                boolean changed = false; //This condition will be changed to true if the position has a bomb and has been modified
                int bombsAround = 0;

                for (int i = 0; i < xPosition.size(); i++) {
                    if (x == xPosition.get(i) && y == yPosition.get(i)) {
                        solution[y][x] = gridSize + 1; //If a bomb is present, the position will have 1 added to it, making 10, along with the changed condition being changed
                        changed = true;
                    }
                }
                if (!changed) {
                    for (int i = 0; i < xPosition.size(); i++) {
                        if (x - 1 == xPosition.get(i) && y == yPosition.get(i)) //Checks if the buttons Left of the one being checked has a bomb or not
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x - 1 == xPosition.get(i) && y - 1 == yPosition.get(i)) //Top Left of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x == xPosition.get(i) && y - 1 == yPosition.get(i)) //Above one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x + 1 == xPosition.get(i) && y - 1 == yPosition.get(i)) //Top Right of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x + 1 == xPosition.get(i) && y == yPosition.get(i)) //Right of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x + 1 == xPosition.get(i) && y - 1 == yPosition.get(i)) //Bottom Right of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x == xPosition.get(i) && y - 1 == yPosition.get(i)) //Bottom of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x - 1 == xPosition.get(i) && y - 1 == yPosition.get(i)) //Bottom Left of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                    }
                    solution[y][x] = bombsAround; //Number of bombs around tile being checked is saved
                }
            }
        }
        for (int i = 0; i < solution.length; i++)
        {
            for (int j = 0; j < solution[0].length; j++)
            {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }
                    ////////////WHEN AN ACTION IS PERFORMED (Tile Is Clicked)\\\\\\\\\\\\
    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (JButton[] button : buttons) { //For Each Loop can only be conducted on the first for loop
            for (int j = 0; j < buttons.length; j++) {
                if (e.getSource() == button[j]) { //Will Check which button was pressed and perform the designated output when the button the action was performed on is found
                    System.out.println("Clicked");
                }
            }
        }
    }
}
