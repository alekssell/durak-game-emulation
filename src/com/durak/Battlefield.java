package com.durak;

import java.util.ArrayList;
import java.util.List;

public class Battlefield {
    // List of cards that adding by attacker and considering defender
    private List<Card> cardsOnTable;

    // Choosing the list of non trumped cards from the deck of attacker.
    // This deck include all of attacker cards if all of them are trumped cards.
    private List<Card> sortedDeckOfPlayer;

    // Picking cards with the same rank for joint attack
    private List<Card> cardsWithTheSameRank;

    // Defender collecting cards for defense
    private List<Card> cardsForDefending;

    // This list is collecting for case if defender will have to take the cards
    // in second or more semi-round
    private List<Card> takeTheseCards;

    Battlefield () {
        this.cardsOnTable = new ArrayList<>();
        this.sortedDeckOfPlayer = new ArrayList<>();
        this.cardsWithTheSameRank = new ArrayList<>();
        this.cardsForDefending = new ArrayList<>();
        this.takeTheseCards = new ArrayList<>();
    }

    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public List<Card> getSortedDeckOfPlayer () {
        return sortedDeckOfPlayer;
    }

    public void setSortedDeckOfPlayer(List<Card> sortedDeckOfPlayer) {
        this.sortedDeckOfPlayer = sortedDeckOfPlayer;
    }

    public List<Card> getCardsWithTheSameRank() {
        return cardsWithTheSameRank;
    }


    public List<Card> getCardsForDefending() {
        return cardsForDefending;
    }

    public List<Card> getTakeTheseCards() {
        return takeTheseCards;
    }
}
