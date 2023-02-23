package com.durak;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> shuffledDeck;
    Deck () {
        this.shuffledDeck = this.newDeck();
    }

    // Creating new deck with 36 cards and shuffling it
    Stack<Card> newDeck () {
        Stack<Card> createdDeck = new Stack<>();
        for (Suit s: Suit.values()){
            for (Rank rank: Rank.values()){
                createdDeck.add(new Card(rank, s));
            }
        }
        Collections.shuffle(createdDeck);
        return createdDeck;
    }

    public Stack<Card> getShuffledDeck() {
        return shuffledDeck;
    }
}
