package com.durak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private static boolean isAllowToChangeRoles = true;
    private List<Card> myDeck = new ArrayList<>();

    public Player(String name, Deck deck) {
        this.name = name;
        this.myDeck = sixCards(deck);
    }

    // Players are getting six cards from the main deck in the beginning of the game.
    // Players getting cards after round of game until
    // they have six cards again while there are cards in the main deck.
    List<Card> sixCards(Deck deck) {
        if (myDeck.size() >= 6 || deck.getShuffledDeck().isEmpty()) {
            return myDeck;
        }
        for (int i = myDeck.size(); i < 6; i++) {
            if (!deck.getShuffledDeck().isEmpty()) {
                myDeck.add(deck.getShuffledDeck().pop());
            }
        }
        return myDeck;
    }

    // Player is attacking. Firstly player is trying to attack with non-trump cards, and
    // he is using trump cards if he doesn't have any ordinary card.
    // Pool of attacking cards is transmitting to the list.
    void attack(Card trump, Battlefield battlefield, Player defenderPlayer) {
        System.out.println("Player " + name + " is attacking");
        System.out.println("...........................................");
        System.out.println("Deck of " + name + " is: " + myDeck);

        // Choosing non trump cards from the deck of player or define that all cards of deck are trumped
        battlefield.setSortedDeckOfPlayer(myDeck.stream()
                .filter(card -> card.getNumSuit() != trump.getNumSuit())
                .collect(Collectors.toList()));
        if (battlefield.getSortedDeckOfPlayer().isEmpty()) {
            battlefield.setSortedDeckOfPlayer(myDeck);
        }

        // Searching the card which is the same to the minimal card by rank for joining the attack
        Card minimalRankCard = Collections.min(battlefield.getSortedDeckOfPlayer()); //Choosing a minimal card by rank
        for (Card card : battlefield.getSortedDeckOfPlayer()) {
            if (card.getNumValue() == minimalRankCard.getNumValue()) {
                battlefield.getCardsWithTheSameRank().add(card);
            }
        }

        // If quantity of the same cards is more than quantity of cards in defender deck,
        // we are cutting the list of the same cards to this size
        if (battlefield.getCardsWithTheSameRank().size() > defenderPlayer.getMyDeck().size()) {
            List<Card> adjustToDefenderDeck = // Temporary list for cutting the size up to deck of defender
                    battlefield.getCardsWithTheSameRank().subList(0, defenderPlayer.getMyDeck().size());
            battlefield.getCardsOnTable().addAll(adjustToDefenderDeck);
        } else {
            battlefield.getCardsOnTable().addAll(battlefield.getCardsWithTheSameRank());
        }
        myDeck.removeAll(battlefield.getCardsOnTable());// Cards that are on the table removing from attacker deck
        battlefield.getSortedDeckOfPlayer().clear();
        battlefield.getCardsWithTheSameRank().clear();

        // Showing on display attacking cards and deck of attacker after move
        for (Card card : battlefield.getCardsOnTable()) {
            System.out.println(card);
        }
        System.out.println("Attacker deck after move: " + myDeck);
        System.out.println("");
        System.out.println("");

    }
    // Player defending ot taking the cards
    void defense(Card trump, Battlefield battlefield) {
        System.out.println("Player " + this.getName() + " is defending");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Deck of " + this.getName() + " is: " + this.getMyDeck());

        // Dividing cards from the deck of defender to trump and non-trump
        List<Card> isTrump = myDeck.stream()
                .filter(card -> card.getNumSuit() == trump.getNumSuit())
                .collect(Collectors.toList());
        Collections.sort(isTrump);
        List<Card> nonTrump = myDeck.stream()
                .filter(card -> card.getNumSuit() != trump.getNumSuit())
                .collect(Collectors.toList());
        Collections.sort(nonTrump);

        // Defender is trying to beat the card firstly with the smallest non-trump card.
        // If he doesn't have any card of needable suit for defense, defender is using the smallest trump card.
        // Cards is collected in list of defending cards.
        for (Card card : battlefield.getCardsOnTable()) {
            for (Card myNonTrumpCard : nonTrump) {
                if (myNonTrumpCard.compareTo(card) > 0 && myNonTrumpCard.getNumSuit() == card.getNumSuit()) {
                    battlefield.getCardsForDefending().add(myNonTrumpCard);
                    myDeck.remove(myNonTrumpCard);
                    break;
                } else if (!isTrump.isEmpty()) {
                    battlefield.getCardsForDefending().add(isTrump.get(0));
                    myDeck.remove(isTrump.get(0));
                    isTrump.remove(0);
                    break;
                }
            }
        }

        // Defender is taking cards if he doesn't have cards for defense
        if (battlefield.getCardsForDefending().size() < battlefield.getCardsOnTable().size()) {
            myDeck.addAll(battlefield.getCardsOnTable());// Taking cards from current semi-round
            myDeck.addAll(battlefield.getTakeTheseCards());// Taking cards from former semi-round
            myDeck.addAll(battlefield.getCardsForDefending());// Taking back cards which were prepared for current defense
            // Showing on display deck of defender after taking cards
            System.out.println("Player " + name + " took cards");
            System.out.println("Defender deck after taking the cards: " + myDeck);
            System.out.println("");
            System.out.println("");
            battlefield.getCardsOnTable().clear();
            battlefield.getTakeTheseCards().clear();
            isAllowToChangeRoles = false;// Prohibited to change player roles Attacker to Defender and vice-versa

        }
        // Defender is beating the cards if defending cards for that were collected
        // Cards from the table and defending cards are collected in another list. These cards will be taken if
        // defender won't beat the cards in next semi-round (if attacker add cards onto the table).
        else {
            for (Card card : battlefield.getCardsOnTable()) {
                battlefield.getTakeTheseCards().add(card);
            }
            for (Card card : battlefield.getCardsForDefending()) {
                battlefield.getTakeTheseCards().add(card);
            }
            battlefield.getCardsOnTable().clear();
            // Showing on display defending cards and deck of defender after defense
            for (Card card : battlefield.getCardsForDefending()) {
                System.out.println(card);
            }
            System.out.println("Defender deck after defense: " + myDeck);
            System.out.println("");
            System.out.println("");
            isAllowToChangeRoles = true;// Allow to change player roles Attacker to Defender and vice-versa
        }
    }

    //Searching if players have any trump cards, null if there is no trump card in the deck
    public Card comparisonWithTrumpcard(Card trumpCard) {
        List<Card> cardsForComparison = new ArrayList<>();
        for (Card card : myDeck) {
            if (card.getNumSuit() == trumpCard.getNumSuit()) {
                cardsForComparison.add(card);
            }
        }
        if (!cardsForComparison.isEmpty()) {
            return Collections.min(cardsForComparison);
        }
        return null;
    }

    // Checking if attacker has cards with the same rank to the cards that were used defender for defense
    boolean addCardsToAttack(Battlefield battlefield, Player defenderPlayer) {
        List<Card> temporaryDeck = new ArrayList<>(myDeck);
        for (Card card : battlefield.getCardsForDefending()) {
            for (Card myCard : temporaryDeck) {
                if (myCard.getNumValue() == card.getNumValue()) {
                    battlefield.getCardsOnTable().add(myCard);
                    myDeck.remove(myCard);
                    // Showing on display additional cards from attacker
                    System.out.println("-------->>>>Attacker added: " + myCard);
                    System.out.println("Deck of attacker after adding cards: " + myDeck);
                    System.out.println("");
                    System.out.println("");
                }
            }
        }
        // If attacker doesn't have additional card, deleting defending cards from the deck of defender
        // and preparing battlefield for the next round
        if (battlefield.getCardsOnTable().isEmpty()) {
            defenderPlayer.getMyDeck().removeAll(battlefield.getCardsForDefending());
            battlefield.getCardsForDefending().clear();
            battlefield.getTakeTheseCards().clear();
            return false;// Finishing the round
        }
        // If additional cards is added preparing for new semi-round, defender will be in game
        battlefield.getCardsForDefending().clear();
        return true;// Game is going to the next semi-round
    }

    String getName() {
        return name;
    }

    public List<Card> getMyDeck() {
        return myDeck;
    }
    public static boolean getIsAllowToChangeRoles() {
        return isAllowToChangeRoles;
    }
}
