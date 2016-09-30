package pokerBase;

import java.util.ArrayList;
import java.util.Collections;

import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.DeckException;

public class Deck {

	private ArrayList<Card> deckCards = new ArrayList<Card>();

	public Deck() {
		int iCardNbr = 1;
		for (eSuit eSuit : eSuit.values()) {
			for (eRank eRank : eRank.values()) {
				//TODO Lab3 - Fix this
				if (eSuit != eSuit.JOKER) 
				{
					deckCards.add(new Card(eSuit, eRank, iCardNbr++));
				}
			}
			 
		}
		Collections.shuffle(deckCards);
	}
	
	public Deck(int NbrOfJokers) {

		//TODO Lab3 - Implement joker constructor
		this();
		for (int i = 1; i <= NbrOfJokers; i++)
		{
			deckCards.add(new Card(eSuit.JOKER, eRank.JOKER, 52 + i));
		}
		Collections.shuffle(deckCards);
	}
	
	
	public Deck(int NbrOfJokers, ArrayList<Card> Wilds) {

		//TODO Lab3 - Implement joker and wild constructor
		this(NbrOfJokers);
		for (Card card : deckCards)
		{
			card.setbWild(false);
			for (Card wild : Wilds)
			{
				if (card.geteRank() == wild.geteRank() && card.geteSuit() == wild.geteSuit())
				{
					card.setbWild(true);
				}
			}
		}
		Collections.shuffle(deckCards);
	 
		
	}
	public Card Draw() throws DeckException{
		if (deckCards.size() == 0){
			throw new DeckException(this);
		}
		return deckCards.remove(0);
	}
}
