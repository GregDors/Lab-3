package pokerExceptions;

import pokerBase.Hand;
import pokerEnums.eHandExceptionType;

public class HandException extends Exception {
	private Hand h;
	private eHandExceptionType e;
	public HandException(Hand h, eHandExceptionType e) {
		this.h = h; 
		this.e = e;
	}
	public Hand getHand(){
		return h;
		
	}
	
	public eHandExceptionType geteHandExceptionType(){
		return e;
	}

}
