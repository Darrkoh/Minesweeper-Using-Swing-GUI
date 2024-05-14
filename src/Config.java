import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

///////////////////////////////////////CONFIGURE GAME MENU\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
public class Config {
    private JPanel rootpanel;
    private JTextField textField;
    private JLabel lbl;
    private JButton confirmButton;

    public Config() {
        //Set up the Frame\\
        JFrame frame = new JFrame("Config");
        frame.setContentPane(rootpanel); //This is to change the contents of the root panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Will Close application on exit
        frame.pack(); //Will fit window to size of contents
        frame.setVisible(true); //So we can see it


        /////WHEN CONFIRM BUTTON IS PRESSED\\\\\
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bombs = 0;
                    try {
                        bombs = Integer.parseInt(textField.getText()); //Will parse the contents of the text box
                    } catch (NumberFormatException error) //If user doesn't enter a number (Enters String)
                    {
                        lbl.setText("Enter a Number between 1-80");
                    }

                    if (bombs >= 1 && bombs <= 80) //If Conditions are met, 'Game will load and bombs' variable will be passed to Game form
                    {
                        MinesweeperGUI GamePage = new MinesweeperGUI(bombs); //Assigns this forms bomb value to the Minesweeper Game's bomb variable
                        GamePage.frame.setVisible(true);
                        frame.dispose(); //Closes form
                    }
                    else //If out of range number is entered, program will reset
                    {
                        lbl.setText("Enter a Number between 1-80");
                    }
            }
        });
    }

}

