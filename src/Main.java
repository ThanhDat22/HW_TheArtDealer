/*The program is written using Java in IntelliJ IDE and Visual Studio Code.
 * This is HW2 project of THE ART DEALER Part 2.
 * Team 1 includes Karli Hubbard, Thanh Dat Nguyen, Tennessee San, and Evgeniya Ziyadova.
 * The program ask player to pick four cards and display all after. Also record the picked cards in an output file call CardsDealt.txt.
 */

//Created by Thanh Dat Nguyen (tnrbg@umsystem.edu) on 02/06/2024
//Last updated by Thanh Dat Nguyen (tnrbg@umsystem.edu) on 02/12/2024
//Last updated by Tennessee San (trs22f@umsystem.edu) on 02/16/2024
//Last updated by Tennessee San (trs22f@umsystem.edu) on 02/18/2024
//Last updated by Tennessee San (trs22f@umsystem.edu) on 02/22/2024
//Last updated by Tennessee San (trs22f@umsystem.edu) on 02/26/2024

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    // Map to store suit abbreviations and their corresponding full names
    private static final Map<String, String> suitMap = new HashMap<>();
    private static final ArrayList<String> suitsEBNF = new ArrayList<>(Arrays.asList("S", "H", "D", "C"));
    private static final ArrayList<String> ranksEBNF = new ArrayList<>(
            Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"));

    static {
        // Initialize the suitMap
        suitMap.put("S", "spades");
        suitMap.put("H", "hearts");
        suitMap.put("D", "diamonds");
        suitMap.put("C", "clubs");
    }

    //main
    public static void main(String[] args) throws IOException {



        // Opens input and output files
        //Author review the I/O method in Chap 17 Liang, Y. Daniel. (2015). Introduction to Java Programming: brief version. Boston :Pearson
        FileInputStream inputFile = new FileInputStream("CardsDealt.txt");
        Scanner scanner = new Scanner(inputFile);
        FileOutputStream outputFile = new FileOutputStream("CardsDealt.txt", true);

        StringBuilder pre_record = new StringBuilder();
        // Reads
        while(scanner.hasNext()) {
            pre_record.append(scanner.next()).append("\n");
        }

        //The output file continue writing data.
        PrintWriter writer = new PrintWriter(outputFile);

        // Author review the I/O method in Chap 17 Liang, Y. Daniel. (2015). Introduction to Java Programming: brief version. Boston :Pearson
        //Create format for the date
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        //Get current date in the format of SimpleDateFormat
        String currentDate = f.format(new Date());

        // Panel to hold the history of picked cards
        JPanel historyPanel = new JPanel(new BorderLayout());
        JTextArea historyTextArea = new JTextArea(20, 20);
        historyTextArea.setEditable(false);
        historyTextArea.setLineWrap(true);
        historyTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        // Set the scroll pane to be focusable
        scrollPane.setFocusable(true);
        // Set focus on the scroll pane
        scrollPane.requestFocusInWindow();



        // Display a prompt explaining the purpose of the code
        JOptionPane.showMessageDialog(null,
                "Welcome to Part 2 of the ART DEALER Game!\n\n" + "In this game, you will be able to pick four unique cards from a deck of playing \ncards. After choosing four cards, you will have the opportunity to continue \nor stop. If you wish to continue, you will be asked to pick four more cards. \nIf you wish to stop, the game will end.\n\nOnce you are ready, click 'OK' to start the game.");

        // Add history panel to frame
        JFrame frame = new JFrame("Card History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(historyPanel);
        frame.pack();
        frame.setVisible(true);

        // Loop to continue the game until the user decides to stop
        do {
            //Call method to pick four unique cards
            ArrayList<String> setCard = pickFourCards(); // Pick four unique cards

            // Write the picked cards and the current date to CardsDealt.txt
            writer.println("" + currentDate + "\n" + String.join(",", setCard));

            //Build text for display in text area
            StringBuilder pickedCardsString = new StringBuilder();
            pickedCardsString.append("Date  : ").append(currentDate).append("\nCards: ");

            //loop card and add name with comma
            for (String card : setCard) {
                pickedCardsString.append(card).append(", ");
            }

            //remove last comma of 4th card name
            pickedCardsString.delete(pickedCardsString.length() - 2, pickedCardsString.length());
            //new line
            pickedCardsString.append("\n");

            historyTextArea.append(pre_record.toString());


            historyTextArea.append(pickedCardsString.toString());
            historyTextArea.setCaretPosition(historyTextArea.getDocument().getLength());
            //show the most recent card

            // Display images of cards
            displayPickedCards(setCard);

            // Ask the user if they want to continue or stop picking cards
            String optionMess;
            do {
                optionMess = JOptionPane.showInputDialog("Do you want to play again?\n\n"
                        + "1. Yes, this game is awesome!\n"
                        + "2. No, this game is boring.\n\n"
                        + "Please enter '1' or '2'.\n\n");

                // Check if the input is not "1" or "2"
                if (!("1".equals(optionMess) || "2".equals(optionMess))) {
                    JOptionPane.showMessageDialog(null, "ERROR! Please enter '1' or '2'.");
                }
            } while (!("1".equals(optionMess) || "2".equals(optionMess)));

            // Check if input is "2" to quit/stop
            if ("2".equals(optionMess)) {
                JOptionPane.showMessageDialog(null, "Thank you for playing. See you next time!");

                // Close the files
                writer.close();
                scanner.close();
                inputFile.close();
                outputFile.close();
                break;
            }

            // End of loop
        } while (true);

    }

    // Method to pick four unique cards
    private static ArrayList<String> pickFourCards() {

        //List to store selected cards
        ArrayList<String> setCard = new ArrayList<>();

        // Loop to pick four unique cards
        while (setCard.size() < 4) {

            // Prompt the user to pick a card
            String pickedCard = JOptionPane.showInputDialog(
                    "Please pick a card to enter: \n\nExample: \n'AC' for Ace of Clubs\n'10D' for 10 of Diamonds\n'KH' for King of Hearts\n'5S' for  of Spades\n\nPlease only use upper case letters and numbers.\n\n");

            // Check if the user click cancel
            if (pickedCard == null)
                System.exit(0);

            // Check if the picked card is valid and not already picked
            if (isValidCard(pickedCard) && !setCard.contains(pickedCard)) {
                setCard.add(pickedCard);
            } else {
                JOptionPane.showMessageDialog(null,
                        "ERROR! Please select a valid card that has not yet been picked.");
            }
        }
        return setCard;
    }

    // Method to display the picked cards
    private static void displayPickedCards(ArrayList<String> setCard) {

        // Create a panel to store the card images
        JPanel cardPanel = new JPanel(new GridLayout(1, 4));

        // Loop each card to the panel
        for (String cardName : setCard) {
            String fileName = convertToFileName(cardName);
            ImageIcon im = new ImageIcon("PlayingCards/" + fileName + ".png");
            Image newIm = im.getImage();
            Image modifiedIm = newIm.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            ImageIcon scaledIm = new ImageIcon(modifiedIm);
            JLabel cardLabel = new JLabel(scaledIm);
            cardPanel.add(cardLabel);
        }

        // Show the panel containing all four cards
        JOptionPane.showMessageDialog(null, cardPanel, "Here are the four cards that you selected:", JOptionPane.PLAIN_MESSAGE);

    }

    // Method to convert card name to filename format
    private static String convertToFileName(String cardName) {
        String cardRank;
        String cardSuit;
        if (cardName.length() == 3) {
            cardRank = cardName.substring(0, 2); // Extract rank
            cardSuit = cardName.substring(2); // Extract suit
        } else {
            cardRank = cardName.substring(0, 1); // Extract rank
            cardSuit = cardName.substring(1); // Extract suit
        }

        // Convert letters to their corresponding numbers
        if (cardRank.equals("A"))
            cardRank = "14";
        else if (cardRank.equals("J"))
            cardRank = "11";
        else if (cardRank.equals("Q"))
            cardRank = "12";
        else if (cardRank.equals("K"))
            cardRank = "13";
        else if (cardRank.equals("1")) // Reject input of "1"
            return null;

        // Convert suit to full suit name
        String suit = suitMap.get(cardSuit); // Get full suit name from suit abbreviation
        return cardRank + "_of_" + suit;
    }

    // Method to check if the picked card is valid
    private static boolean isValidCard(String pickedCard) {

        // Check if the card has 2 to 3 characters of input
        if (pickedCard.length() != 2 && pickedCard.length() != 3)
            return false;

        // Check if the rank and suit are valid
        String rank = pickedCard.substring(0, pickedCard.length() - 1);
        char suit = pickedCard.charAt(pickedCard.length() - 1);

        // Prevent input of "1"
        if (rank.equals("1"))
            return false;

        // Allow input of "10"
        if (!rank.equals("10") && !(rank.equals("A") || rank.equals("J") || rank.equals("Q") || rank.equals("K"))
                && !Character.isDigit(rank.charAt(0)))
            return false;

        return (suitsEBNF.contains(Character.toString(suit)) && ranksEBNF.contains(rank));
    }
}