import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGUI implements ActionListener {

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

    ///////////////////////////////Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public MinesweeperGUI() {
        //Instantiate Variables\\
        Scanner inputScanner = new Scanner(System.in); //For User Inputs
        boolean condition;
        xPosition = new ArrayList<>();
        yPosition = new ArrayList<>(); //Instantiates Arraylists for coordinates
        gridSize = 9;
        random = new Random(); //Instantiate the random variable

        //User Selects How Many Bombs They want\\
        do {
            condition = true;
            try {
                System.out.println("How many Bombs do you want (Above 0 and Up to 80)"); //No more than 80 can be generated and 81 would cause there to be no winner
                bombs = inputScanner.nextInt();
            } catch (InputMismatchException e) //If user doesn't enter a number
            {
                System.out.println("Enter a Number between 1-80");
                condition = false; //So Loop Continues
            }

            if (bombs < 1 || bombs > 80)
            {
                System.out.println("Enter a Number between 1-80");
                condition = false; //So Loop Continues
            }
        } while (!condition);

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

        //For Grammatical consistency. Will Display a different title message if 1 bomb is present
        if (bombs > 1) {
            textLabel.setText("There are " + bombs + " Bombs");
        }
        else {
            textLabel.setText("There is " + bombs + " Bomb");
        }

        solution = new int[gridSize][gridSize]; //Creates a solution array to display the games solution\\

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

        //Combining elements\\
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

        //To Prevent Overlapping of bombs (Needs to be checked as soon as possible)\\
                    //THIS SHOULD ONLY HAPPEN IF THERE'S MORE THAN 1 BOMB//
        if (bombs > 1)
        { for (int i = 0; i < bombs; i++) //First Bomb Location
            {
                for (int j = i + 1; i < bombs; i++) //Another Bomb's Location
                {
                    if (xPosition.get(i).equals(xPosition.get(j)) && yPosition.get(i).equals(yPosition.get(j))) //If the positions of the 2 bombs are exactly the same
                    {
                        xPosition.set(j, random.nextInt(gridSize));
                        yPosition.set(j, random.nextInt(gridSize)); //Gives the secondary bomb a new X and Y Value

                        i = 0;
                        j = 0; //Resetting counters
                    }
                }
            }
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
                        break;
                    }
                }
                if (!changed) {
                    for (int i = 0; i < xPosition.size(); i++) {

                        //If statements used to check every position next to the button being checked. Not using case as I'd have to define a default case which is obsolete as the counter will be 0 if now is statement conditions are met.

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
                        if (x + 1 == xPosition.get(i) && y + 1 == yPosition.get(i)) //Bottom Right of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x == xPosition.get(i) && y + 1 == yPosition.get(i)) //Bottom of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                        if (x - 1 == xPosition.get(i) && y + 1 == yPosition.get(i)) //Bottom Left of one being checked
                        {
                            bombsAround++; //Adds 1 to bombs around counter
                        }
                    }
                    solution[y][x] = bombsAround; //Number of bombs around tile being checked is saved
                }
            }
        }

        ////////////////THIS WILL PRINT THE SOLUTION IN THE CONSOLE BOX AUTOMATICALLY! UNCOMMENT OUT IF YOU WISH TO USE\\\\\\\\\\\\\\\\
        //Prints the solution to the minesweeper game (How many bombs are around each button and where each bomb is)

       /* for (int[] solutions : solution) {
            for (int j = 0; j < solution[0].length; j++) {
                System.out.print(solutions[j] + " ");
            }
            System.out.println();
        }*/

    }

    //////////Check's to see if user has hit a bomb. If not then Number of bombs around button pressed is outputted.\\\\\\\\\\
    public void check(int y, int x) //Changes button to display the corresponding solution when clicked and if a bomb is pressed will end game
    {
        boolean gameOver = false; //Game over condition

        //If user Clicked a bomb\\
        if (solution[y][x] == (gridSize+1))
        {
            GameOver(false); //Triggers game over operation
            gameOver = true; //If a Bomb is pressed, Game Over
        }

        //If user didn't click a bomb\\
        if (!gameOver)
        {
            buttons[y][x].setText(String.valueOf(solution[y][x])); //Will change text and button colour. Text will now display the amount of bombs around this button
            buttons[y][x].setBackground(Color.GRAY);
            buttons[y][x].setForeground(Color.WHITE);
        }
    }


    //////////Determines whether user has won yet by checking how many bombs are left\\\\\\\\\\
    public void Winner()
    {
        int buttonsleft = 0;
        for (JButton[] button : buttons) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (button[j].getText().equals(" ")) //Each button that hasn't been clicked adds 1 to the counter
                {
                    buttonsleft++;
                }
            }
        }
        if (buttonsleft == bombs) //Once the amount of buttons left is equal the amount of bombs there are, the game will end with a winning screen
        {
            GameOver(true);
        }
    }

    public void GameOver(boolean condition) {
        //If User Wins\\
        if (condition) {
            textLabel.setForeground(Color.GREEN);
            textLabel.setText("You Win!!!"); //Title becomes Green and congratulates user for winning
        }

        //If User Clicks a Bomb\\
        if (!condition) {
            textLabel.setForeground(Color.RED);
            textLabel.setText("Game Over!"); //Title becomes red and says "Game Over!"
        }

        //Disable Buttons//
        for (JButton[] button : buttons)
        {
            for (int j = 0; j < buttons[0].length; j++)
            {
                button[j].setEnabled(false);
            }
        }
    }
                    ////////////WHEN AN ACTION IS PERFORMED (Tile Is Clicked)\\\\\\\\\\\\
    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (e.getSource() == buttons[i][j]) { //Will Check which button was pressed and perform the designated output when the button the action was performed on is found
                    check(i, j); //Runs a check on button just pressed
                    Winner(); //Runs a check on if user has won this turn
                }
            }
        }
    }
}
