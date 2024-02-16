/** The program is written using Java in IntelliJ IDE.
 *  This is The project 1 of team 1.
 *  Team 1 includes Karli Hubbard, Thanh Dat Nguyen, Aaron Radcliffe, Tennessee San, and Evgeniya Ziyadova.
 *  The Program deals 4 cards to guesses and records the cards in output file.
 */

// Created by Thanh Dat Nguyen( tnrbf@umsystem.edu) on 02/06/2024

// Last updated by Thanh Dat Nguyen( tnrbf@umsystem.edu) on 02/12/2024

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<String> suitsEBNF = new ArrayList<>(Arrays.asList("S", "H", "D", "C" ));
        ArrayList<String> ranksEBNF = new ArrayList<>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" ));


        // Opens output file
        //Author review the I/O method in Chap 17 Liang, Y. Daniel. ( 2015). Introduction to java programming : brief version. Boston :Pearson
        FileOutputStream outputFile = new FileOutputStream("CardsDealt.txt",true); // The input file continue writing data.
        PrintWriter writer = new PrintWriter(outputFile);

        // Write the time into output file
        //Author review the Date Object in Chap 2 Liang, Y. Daniel. ( 2015). Introduction to java programming : brief version. Boston :Pearson
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = f.format(new Date());
        writer.println(currentDate);


        // Display the announcement of the program’s purpose
        JLabel programPurposeMes = new JLabel("\nWelcome to the Art Dealer Game!\n"
                + "The Art Dealer is a card game for fun.\n"
                +"Are you ready\n");
        programPurposeMes.setFont(new Font("Arial", Font.BOLD, 48));
        JOptionPane.showMessageDialog(null, programPurposeMes);

        //  When the program executes, four cards should appear on the screen.
        do {
            ArrayList<String> setCard = new ArrayList<>(); // Holds 4 name of Cards
            ArrayList<String> cardSetEBNF = new ArrayList<>();

            int i = 0;
            do {
                Card ca = new Card();
                ca.randomCard(); // Generates a card
                String target = ca.toString(); // gets the name of the card
                if(!setCard.contains(target)){ // if the card does exist in set of 4, adds card to the set
                    setCard.add(target);

                    // Prepares the string of card name in EBNF grammar
                    int supIndex = ca.getSuit();
                    int preIndex = ca.getRank();
                    String caEBNF = ranksEBNF.get(preIndex) + suitsEBNF.get(supIndex);
                    cardSetEBNF.add(caEBNF);

                }
                i++;
            } while(i < 4); // End of do-while loop; Users had 4 cards.

            // Writes 4 card into input file
            for(int j = 0; j < 3; ++j){
                writer.print(cardSetEBNF.get(j) + ",");
            }
            writer.println(cardSetEBNF.get(3));



            // Display the name of 4 cards
            System.out.println(Arrays.toString(setCard.toArray()));

            //Display the image of cards:

            i = 0;
            do {
                // Display cards to users
                String cardName = setCard.get(i);
                ImageIcon im = new ImageIcon("PlayingCards/"+cardName + ".png");
                Image newIm = im.getImage();
                Image modifiedIm = im.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                im = new ImageIcon(modifiedIm);
                JOptionPane.showMessageDialog(null,cardName, "Display Image", JOptionPane.INFORMATION_MESSAGE,im);
                i++;
            } while( i < 4);

            // Display an options menu
            String optionMess = JOptionPane.showInputDialog("\nSelect your option in the menu below:\n"
                    + "1. Get another 4 Card dealt.\n"
                    + "2. Exit\n");
            switch(optionMess){
                case "1":
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null,"Thank yor for playing. See you next time.");

                    //Closing the files
                    writer.close();
                    outputFile.close();
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null,"Enter a valid choice.");
                    break;
            }
        }  while(true); // End of do-while loop

    }
}