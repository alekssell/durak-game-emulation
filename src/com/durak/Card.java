package com.durak;

import java.util.Objects;

public class Card implements Comparable<Card> {
    Suit cardSuit;
    Rank cardValue;
    private int numValue;
    private int numSuit;

    public Card (Rank cardValue, Suit cardSuit) {
        this.cardValue = cardValue;
        this.cardSuit = cardSuit;
        switch (cardValue){
            case SIX:
                numValue = 6;
                break;
            case SEVEN:
                numValue = 7;
                break;
            case EIGHT:
                numValue = 8;
                break;
            case NINE:
                numValue = 9;
                break;
            case TEN:
                numValue = 10;
                break;
            case JACK:
                numValue = 11;
                break;
            case QUEEN:
                numValue = 12;
                break;
            case KING:
                numValue = 13;
                break;
            case ACE:
                numValue = 14;
                break;
        }
        switch (cardSuit) {
            case HEARTS:
                numSuit = 0;
                break;
            case DIAMONDS:
                numSuit = 1;
                break;
            case CLUBS:
                numSuit = 2;
                break;
            case SPADES:
                numSuit = 3;
                break;
        }
    }
    int getNumValue () {
        return numValue;
    }
    int getNumSuit () {
        return numSuit;
    }

    @Override
    public String toString () {
        return cardValue + " of " + cardSuit;
    }

    @Override
    public int compareTo( Card o) {
            return this.numValue - o.numValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return numValue == card.numValue && numSuit == card.numSuit && cardSuit == card.cardSuit && cardValue == card.cardValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardSuit, cardValue, numValue, numSuit);
    }
}
