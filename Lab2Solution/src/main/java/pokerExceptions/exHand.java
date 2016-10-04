package pokerExceptions;

import pokerBase.Hand;

public class exHand extends Exception{
	private Hand h;
	
	public exHand(Hand h) {
		this.h = h;
		
	}
	public Hand getHand(){
		return h;
	}

}
