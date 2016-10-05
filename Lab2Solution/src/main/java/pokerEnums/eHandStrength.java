package pokerEnums;

public enum eHandStrength {

	RoyalFlush(100, "isHandRoyalFlush") {
		public String toString() {
			return "Royal Flush";
		}
	},
	StraightFlush(90, "isHandStraightFlush") {
		public String toString() {
			return "Straight Flush";
		}
	},
	FiveOfAKind(80, "isHandFiveOfAKind"){
		public String toString() {
			return "Five of a Kind";
		}
	},
	FourOfAKind(70, "isHandFourOfAKind") {
		public String toString() {
			return "Four of a Kind";
		}
	},
	FullHouse(60, "isHandFullHouse") {
		public String toString() {
			return "Full House";
		}
	},
	Flush(50, "isHandFlush") {
		public String toString() {
			return "Flush";
		}
	},
	Straight(40, "isHandStraight") {
		public String toString() {
			return "Straight";
		}
	},
	ThreeOfAKind(30, "isHandThreeOfAKind") {
		public String toString() {
			return "Three of a Kind";
		}
	},
	TwoPair(20, "isHandTwoPair") {
		public String toString() {
			return "Two Pairs";
		}
	},

	Pair(10, "isHandPair") {
		public String toString() {
			return "One Pair";
		}
	},
	HighCard(0, "isHandHighCard") {
		public String toString() {
			return "High Card";
		}
	};

	private eHandStrength(final int handstrength, final String EvalMethod) {
		this.iHandStrength = handstrength;
		this.strEvalMethod = EvalMethod;
	}

	private int iHandStrength;
	private String strEvalMethod;

	public int getHandStrength() {
		return iHandStrength;
	}

	public String getEvalMethod() {
		return this.strEvalMethod;
	}

}
