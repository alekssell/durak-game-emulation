package com.durak;

public class Game {
    private final Deck gameDeck;
    private final Player one;
    private final Player two;
    private Player attackerPlayer;
    private Player defenderPlayer;
    private final Card trumpCard;
    private final Battlefield battlefield;


    public static void main(String[] args) {
        Game game = new Game();
    }

    Game() {
        System.out.println("New game is starting...");

        // Creating a new deck and shuffling it
        this.gameDeck = new Deck();
        System.out.println("The deck is shuffled and ready..");

        // Creating new battlefield
        this.battlefield = new Battlefield();

        // Creating players and handing them out six cards each
        this.one = new Player("Larry", gameDeck);
        this.two = new Player("Vadym", gameDeck);
        System.out.println("New players created: player one - " + one.getName() + ", player two - " + two.getName());

        // Determining the trump card, showing it and returning it back to the bottom of the deck
        this.trumpCard = gameDeck.getShuffledDeck().pop();
        System.out.println("The trumpcard is: " + trumpCard);
        gameDeck.getShuffledDeck().add(0, trumpCard);

        //Searching if players have any trump cards,
        //determining the smallest rank of trump card and compare the smallest trump cards of both of players
        //to get the player who are going to be the first attacker
        //If both of them don't have any trump card then the first attacker is player one
        Card player1Trump = one.comparisonWithTrumpcard(trumpCard);
        Card player2Trump = two.comparisonWithTrumpcard(trumpCard);
        if (player1Trump == null && player2Trump == null) {
            attackerPlayer = one;
            defenderPlayer = two;
            System.out.println("Players don't have any trump cards, that;s why the first attacker is  "
                    + attackerPlayer.getName());
        } else if (player1Trump == null && player2Trump != null) {
            attackerPlayer = two;
            defenderPlayer = one;
            System.out.println("Player " + one.getName() + " doesn't have any trump cards, that's why the first attacker is "
                    + attackerPlayer.getName());
        } else if (player1Trump != null && player2Trump == null) {
            attackerPlayer = one;
            defenderPlayer = two;
            System.out.println("Player " + two.getName() + " doesn't have any trump cards, that's why the first attacker is "
                    + one.getName());
        } else if (player1Trump.compareTo(player2Trump) > 0) {
            attackerPlayer = two;
            defenderPlayer = one;
            System.out.println("Player " + two.getName() + " has smaller trump card, that's why the first attacker is "
                    + two.getName());
        } else {
            attackerPlayer = one;
            defenderPlayer = two;
            System.out.println("Player " + one.getName() + " has smaller trump card, that's why the first attacker is "
                    + one.getName());
        }
        System.out.println("Attention to the table-->");
        System.out.println("");

        //Starting moves
        onTable();
    }

    void onTable() {
        while (!attackerPlayer.getMyDeck().isEmpty() && !defenderPlayer.getMyDeck().isEmpty()) {
            round();
            attackerPlayer.sixCards(gameDeck);// Players getting cards after round of the game until they have six
            defenderPlayer.sixCards(gameDeck);
            // If defender took cards in the last rount then players aren't changing roles
            if (Player.getIsAllowToChangeRoles()) {
                if (attackerPlayer.equals(one)) {
                    attackerPlayer = two;
                    defenderPlayer = one;
                } else {
                    attackerPlayer = one;
                    defenderPlayer = two;
                }
            }
        }
        // If there are no cards in the main, the winner is who first used all cards from hus own deck
        // If both of player decks is empty the friendship won the game
        if (attackerPlayer.getMyDeck().isEmpty()) {
            System.out.println("------------------- Congratulations! -----------------------");
            System.out.println("Player " + attackerPlayer.getName() + " won the game");
        } else if (attackerPlayer.getMyDeck().isEmpty() && defenderPlayer.getMyDeck().isEmpty()) {
            System.out.println("------------------- Congratulations! -----------------------");
            System.out.println("Friendship won the game");
        } else {
            System.out.println("------------------- Congratulations! -----------------------");
            System.out.println("Player " + defenderPlayer.getName() + " won the game");
        }
    }

    // Players are playing one round when someone is attacker and someone is defender
    void round() {
        boolean localRoundFinished = true;
        attackerPlayer.attack(trumpCard, battlefield, defenderPlayer);
        while (localRoundFinished) {
            defenderPlayer.defense(trumpCard, battlefield);
            localRoundFinished = attackerPlayer.addCardsToAttack(battlefield, defenderPlayer);
        }
        battlefield.getCardsOnTable().clear();
    }

}



