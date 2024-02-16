/** The program is written using Java in IntelliJ IDE.
 *  This is The project 1 of team 1.
 *  Team 1 includes Karli Hubbard, Thanh Dat Nguyen, Aaron Radcliffe, Tennessee San, and Evgeniya Ziyadova.
 *  The card class represent a card of a 52 card in a set.
 */

// Created by Thanh Dat Nguyen( tnrbf@umsystem.edu) on 02/06/2024

// Last updated by Thanh Dat Nguyen( tnrbf@umsystem.edu) on 02/12/2024

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*A card class */
public class Card {
    // Data fields;
    private int rank; // Holds index of suits in list
    private int suit; // Holds index of rank in list
    public static ArrayList<String> suits = new ArrayList<>(Arrays.asList("spades", "hearts", "diamonds", "clubs" ));
    public static ArrayList<String> ranks = new ArrayList<>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" ));

    // Constructions
    public Card(){} // Default constructor

    //Constructor with initial rank and suit
    public Card(int rank, int suit){
        this.rank = rank;
        this.suit = suit;
    }

    // Getters

    /**
     * Reads the index of rank of the card
     * @return: the index of rank of the card
     */
    public int getRank() {
        return rank;
    }

    /**
     * Reads the index of the suit of the card.
     * @return: the index of suit of the card
     */
    public int getSuit() {
        return suit;
    }

    // Setters

    /**
     * Updates the index of rank of the card.
     * @param rank: updated the index of rank fo the card
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Updates the index of suit of the card
     * @param suit: Updated the index of the suit of the card
     */
    public void setSuit(int suit) {
        this.suit = suit;
    }

    // Methods

    /**
     * Generates one of 52 cards randomly
     * @return: a card from a set of 52 cards
     */
    public Card randomCard(){
        setRank(randomNo(0,ranks.size()-1));
        setSuit(randomNo(0, suits.size() -1));
        return new Card(rank, suit);
    }

    /**
     * Generates a random number in a range.
     * This function
     * @param start: the lowest bound of range.
     * @param end: the highest bound of range.
     * @return: the random number
     * * Author review the random method in Chap 3 Liang, Y. Daniel. ( 2015). Introduction to java programming : brief version. Boston :Pearson
     */
    private int randomNo(int start, int end){
        Random random = new Random();
        return random.nextInt(end - start +1) + start;
        //return (start + (int)(Math.random() * ((end - start) + 1)));
    }

    /**
     * Display a card
     * @return: a string that display a card
     */
    @Override
    public String toString(){
        return ranks.get(rank) + "_of_" + suits.get(suit);
    }

}