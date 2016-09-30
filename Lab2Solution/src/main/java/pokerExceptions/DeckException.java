package pokerExceptions;

import pokerBase.Deck;

public class DeckException extends Exception{
	private Deck d;
	public DeckException(Deck d) {
		this.d = d;
	}
	
	public Deck getDeck(){
		return d;
	}

}
