# durak-game-emulation
The emulation of card game "Durak" between two players

Durak (Дурак, in English "fool") is a card game that is popular in many post-Soviet states.

Article about this card game https://en.wikipedia.org/wiki/Durak
## Implementation 
This is my very first expirience in java. 

Program is emulating card game beetween the two players and showing all game in console or terminal.
### Short description of this emulation

The names of players is given by program. The deck is shuffles, and each player is dealt six cards. The thirteenth card of the deck turns face and shows up on the screen, it's suit determining the trump suit for the current deal, after that it returns back to the beneath of deck.

The player who has the lowest trump card will be the first attacker. If no one have the trump card, player "Larry" will be the first attacker.

**Round starts.** Attacker picks the smallest card from his deck which is not trumped. If all cards in his deck are trumped then he picks the smallest from them. Then he picks another cards that have the same rank to the smallest to attack with them and finally makes a move. We can watch on the screen his deck before and after move and cards that were picked for attack.

Defender pick all cards from his deck that fit by suit for defense to every next card on the table, choose from them the smallest from the non trumped cards and defends. If defender doesn't have any non trumped card of needable suit, he uses the smallest trump card. If defender doesn't have any card for defense he takes all cards which was added by attacker. We can watch on the screen his deck before and after move and cards that were picked for defense. We see the phrase about taking of cards by defender if he does it.

In case when defender beats all cards, attacker verifies if he has the cards the same rank to cards which were used defender for defense. If have, he addem them. We can watch additional cards  on the screen and deck of attacker after adding.

Defender must defense until either attacker doesn't have cards for adding or defender doesn't have cards for defense and has to rake the cards.

**Next round.** If defender take cards in the previous round, then attacker won't be changed otherwise attacker becomes defender, players changes their roles. Players getting cards after round of game until they have six cards again while there are cards in the main deck.

The winner is player who first use all of his cards provided that there are not cards in the main deck. If they loose their last cards in the same round then it is draw.

### Screenshot of screen
![Screenshot_3](https://user-images.githubusercontent.com/126061301/220875125-52534f84-ef7f-4fe3-a9d2-8b42b161cc2c.jpg)
