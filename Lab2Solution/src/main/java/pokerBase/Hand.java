package pokerBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pokerEnums.eCardNo;
import pokerEnums.eHandExceptionType;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.HandException;
import pokerExceptions.exHand;

public class Hand {

	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	private boolean bScored;
	private HandScore hs;

	private ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	void AddToCardsInHand(Card c) {
		CardsInHand.add(c);
	}

	public boolean isbScored() {
		return bScored;
	}

	public HandScore getHs() {
		return hs;
	}

	public void EvaulateHand() {
		try {
			Hand h = EvaluateHand(this);
			h.hs = h.getHs();
			h.bScored = h.bScored;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand{
		//Hand a = Hands.get(0);
		//for(int i = 0; i< Hands.size();i++){}
		Hand h = Hands.get(0);
		/**Collections.sort(Hands,new Comparator<Hand>(){
			@Override
	        public int compare(Hand Hand1, Hand Hand2)
	        {
				HandScore hs1 = ((Hand) Hand1).getHs();
				HandScore hs2 = ((Hand) Hand2).getHs();
	            return ((Comparable) Hand1.getHs()).compareTo(Hand2.getHs());
	        }
	    });
			
		
		if(Hands.get(Hands.size()-1) == Hands.get(Hands.size() - 2)){
			throw new exHand(this);//Fix this
		}
		return Hands.get((Hands.size()-1));
	*/	
		if (Hands.size() != 1) {
			Hand BestHand = Hands.get(0);
			for (int i = 1; i < Hands.size() - 1; i++) {
				if (BestHand.getHs().getHandStrength() < Hands.get(i).getHs().getHandStrength()) {
					BestHand = Hands.get(i);
				}
				else if (BestHand.getHs().getHiHand() < Hands.get(i).getHs().getHiHand()) {
					BestHand = Hands.get(i);
				}
				else if (BestHand.getHs().getLoHand() < Hands.get(i).getHs().getLoHand()) {
					BestHand = Hands.get(i);				
				}
				else{
					for (int j = 0; j < BestHand.getHs().getKickers().size(); j++) {
						if (BestHand.getHs().getKickers().get(j).geteRank().getiRankNbr() < Hands.get(i).getHs().getKickers().get(j).geteRank().getiRankNbr()) {
							BestHand = Hands.get(i);
							break;
						}
					}
				}
				
			}
			h = BestHand;
		}
		return h;
		
	}
	


	/**
	 * <b>EvaluateHand</b> is a static method that will score a given Hand of
	 * cards
	 * 
	 * @param h
	 * @return
	 * @throws HandException
	 */
	static Hand EvaluateHand(Hand h) throws Exception {

		// Sort the colleciton (by hand rank)
		Collections.sort(h.getCardsInHand());

		// TODO - Lab 3 Here's the code to throw the HandException
		// TODO - Implement HandException

		//if (h.getCardsInHand().size() != 5) {
		//	throw new HandException(h, eHandExceptionType.ShortHand);
		//}

		ArrayList<Hand> ExplodedHands = new ArrayList<Hand>();
		ExplodedHands.add(h);

		ExplodedHands = ExplodeHands(ExplodedHands);

		for (Hand hEval : ExplodedHands) {
			if (h.getCardsInHand().size() != 5) {
				throw new HandException(h, eHandExceptionType.ShortHand);
			}
			Collections.sort(h.getCardsInHand());
			

			HandScore hs = new HandScore();
			try {
				Class<?> c = Class.forName("pokerBase.Hand");

				for (eHandStrength hstr : eHandStrength.values()) {
					Class[] cArg = new Class[2];
					cArg[0] = pokerBase.Hand.class;
					cArg[1] = pokerBase.HandScore.class;

					Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
					Object o = meth.invoke(null, new Object[] { hEval, hs });

					// If o = true, that means the hand evaluated- skip the rest
					// of
					// the evaluations
					if ((Boolean) o) {
						break;
					}
				}

				hEval.bScored = true;
				hEval.hs = hs;

			} catch (ClassNotFoundException x) {
				x.printStackTrace();
			} catch (IllegalAccessException x) {
				x.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		if (ExplodedHands.size() != 1) {
			Hand BestHand = ExplodedHands.get(0);
			for (int i = 1; i < ExplodedHands.size() - 1; i++) {
				if (BestHand.getHs().getHandStrength() < ExplodedHands.get(i).getHs().getHandStrength()) {
					BestHand = ExplodedHands.get(i);
				}
				else if (BestHand.getHs().getHiHand() < ExplodedHands.get(i).getHs().getHiHand()) {
					BestHand = ExplodedHands.get(i);
				}
				else if (BestHand.getHs().getLoHand() < ExplodedHands.get(i).getHs().getLoHand()) {
					BestHand = ExplodedHands.get(i);				
				}
				else {
					for (int j = 0; j < BestHand.getHs().getKickers().size(); j++) {
						if (BestHand.getHs().getKickers().get(j).geteRank().getiRankNbr() < ExplodedHands.get(i).getHs().getKickers().get(j).geteRank().getiRankNbr()) {
							BestHand = ExplodedHands.get(i);
							break;
						}
					}
				}
			}
			h = BestHand;
		}
		
		return h;
	}
	

	/**
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */

	private static ArrayList<Hand> ExplodeHands(ArrayList<Hand> Hands) {
		Hand hand = Hands.get(0);
		for (int i = 0; i < 5; i++) {
			if (hand.getCardsInHand().get(i).geteSuit() == eSuit.JOKER) {
				if (Hands.size() == 1) {
					Hands.remove(0);
					for (eSuit Suit : eSuit.values()) {
						for (eRank Rank : eRank.values()) {
							if ((Suit != eSuit.JOKER) && (Rank != eRank.JOKER)){
								hand.getCardsInHand().get(i).seteSuit(Suit);
								hand.getCardsInHand().get(i).seteRank(Rank);
								Collections.sort(hand.getCardsInHand());
								Hands.add(hand);
							}
						}
					}
				}
				else {
					ArrayList<Hand> iHands = new ArrayList<Hand>();
					for (Hand iHand : Hands) {
						for (eSuit Suit : eSuit.values()) {
							for (eRank Rank : eRank.values()) {
								if ((Suit != eSuit.JOKER) && (Rank != eRank.JOKER)){
									iHand.getCardsInHand().get(i).seteSuit(Suit);
									iHand.getCardsInHand().get(i).seteRank(Rank);
									Collections.sort(iHand.getCardsInHand());
									iHands.add(iHand);
								}
							}
						}
					}
					Hands = iHands;
				}
			}
		}
		return null;
	
	}
	
	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {

		Card c = new Card();
		boolean isRoyalFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			if (c.geteRank() == eRank.ACE) {
				isRoyalFlush = true;
				hs.setHandStrength(eHandStrength.RoyalFlush.getHandStrength());
				hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
				hs.setLoHand(0);
			}
		}
		return isRoyalFlush;
	}

	/**
	 * isHandStraightFlush - Will return true if the hand is a straight flush
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandStraightFlush(Hand h, HandScore hs) {
		Card c = new Card();
		boolean isStraightFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			isStraightFlush = true;
			hs.setHandStrength(eHandStrength.StraightFlush.getHandStrength());
			hs.setHiHand(c.geteRank().getiRankNbr());
			hs.setLoHand(0);
		}

		return isStraightFlush;
	}

	/**
	 * isHandFiveOfAKind - this method will determine if the hand is a five of a
	 * kind
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFiveOfAKind(Hand h, HandScore hs) {
		boolean bHandCheck = false;
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FiveOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
		}

		return bHandCheck;
	}

	/**
	 * isHandFourOfAKind - this method will determine if the hand is a four of a
	 * kind
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);

		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setKickers(kickers);
		}

		return bHandCheck;
	}

	/**
	 * isHandFullHouse - This method will determine if the hand is a full house
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		}

		return isFullHouse;

	}

	public static boolean isHandFlush(Hand h, HandScore hs) {

		boolean bIsFlush = false;
		if (isHandFlush(h.getCardsInHand())) {
			hs.setHandStrength(eHandStrength.Flush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
			bIsFlush = true;
		}

		return bIsFlush;
	}

	public static boolean isHandFlush(ArrayList<Card> cards) {
		int cnt = 0;
		boolean bIsFlush = false;
		for (eSuit Suit : eSuit.values()) {
			cnt = 0;
			for (Card c : cards) {
				if (c.geteSuit() == Suit) {
					cnt++;
				}
			}
			if (cnt == 5)
				bIsFlush = true;

		}
		return bIsFlush;
	}

	public static boolean isStraight(ArrayList<Card> cards, Card highCard) {
		boolean bIsStraight = false;
		boolean bAce = false;

		int iStartCard = 0;
		highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
		highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());

		if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) {
			// First card is an 'ace', handle aces
			bAce = true;
			iStartCard++;
		}

		for (int a = iStartCard; a < cards.size() - 1; a++) {
			if ((cards.get(a).geteRank().getiRankNbr() - cards.get(a + 1).geteRank().getiRankNbr()) == 1) {
				bIsStraight = true;
			} else {
				bIsStraight = false;
				break;
			}
		}

		if ((bAce) && (bIsStraight)) {
			if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) {
				highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());
			} else if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) {
				highCard.seteRank(cards.get(eCardNo.SecondCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.SecondCard.getCardNo()).geteSuit());
			} else {
				bIsStraight = false;
			}
		}
		return bIsStraight;
	}

	public static boolean isHandStraight(Hand h, HandScore hs) {

		boolean bIsStraight = false;
		Card highCard = new Card();
		if (isStraight(h.getCardsInHand(), highCard)) {
			hs.setHandStrength(eHandStrength.Straight.getHandStrength());
			hs.setHiHand(highCard.geteRank().getiRankNbr());
			hs.setLoHand(0);
			bIsStraight = true;
		}
		return bIsStraight;
	}

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean isThreeOfAKind = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));

		}

		if (isThreeOfAKind) {
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setLoHand(0);
			hs.setKickers(kickers);
		}

		return isThreeOfAKind;
	}

	public static boolean isHandTwoPair(Hand h, HandScore hs) {

		boolean isTwoPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isTwoPair;
	}

	public static boolean isHandPair(Hand h, HandScore hs) {
		boolean isPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isPair;
	}

	public static boolean isHandHighCard(Hand h, HandScore hs) {
		hs.setHandStrength(eHandStrength.HighCard.getHandStrength());
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		hs.setLoHand(0);
		ArrayList<Card> kickers = new ArrayList<Card>();
		kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setKickers(kickers);
		return true;
	}
}
