package sample.poker;

public class VHand {
	VCard[] hand;
	String[] HAND_TYPE = new String[] { "High Card", "One Pair", "Two Pair",
			"Three of a Kind", "Straight", "Flush", "Full House",
			"Four of a Kind", "Straight Flush" };

	/*
	 * Rank of Best Hands Straight Flush - 8 Four of a Kind - 7 Full House - 6
	 * Flush - 5 Straight - 4 Three of a Kind - 3 Two Pair - 2 One Pair - 1 High
	 * Card - 0
	 */
	public VHand(VCard[] h) {
		sortHand(h);
		hand = h;

	}

	public void sortHand(VCard[] cards) {
		// boolean swapped = false;
		for (int i = 0; i < cards.length - 1; i++) {
			for (int j = 0; j < cards.length - 1; j++) {
				if (cards[j].getFaceValue() < cards[j + 1].getFaceValue()) {
					VCard placeholder = cards[j];
					cards[j] = cards[j + 1];
					cards[j + 1] = placeholder;
					// swapped = true;
				}
			}

		}
/*		for (VCard c : cards) {
			System.out.println("Card: " + c.getCardValue() + " Suit: "
					+ c.getSuit());
		} */
	}

	public int[] getHand() {
		if (this.flushCheck() != null) {
		//	System.out.println("FLUSH");
			// Straight Flush
			if (this.straightCheck() != null) {
		//		System.out.println("STRAIGHT");
				return new int[] { 8, this.straightCheck()[1] };
			}
			return this.flushCheck();
		}
		if (this.straightCheck() != null) {
			return this.straightCheck();
		}
		if (this.kindCheck() != null) {
			return this.kindCheck();
		} else {
			return this.getHighCardValue();
		}
	}

	private int[] getHighCardValue() {
		return new int[] {
				0,
				hand[0].getFaceValue() * 160000 + hand[1].getFaceValue() * 8000
						+ hand[2].getFaceValue() * 400 + hand[3].getFaceValue()
						* 20 + hand[4].getFaceValue() };
	}

	// assume VCard[] is sorted high to low
	private int[] flushCheck() {
		String suit = hand[0].getSuit();
		for (VCard card : hand) {
			if (!(card.getSuit().equals(suit))) {
				return null;
			}
		}

		int val2 = hand[hand.length - 1].getFaceValue();
		int num = 20;
		for (int i = hand.length - 2; i <= 0; i--) {
			val2 += hand[i].getFaceValue() * num;
			num *= 20;
		}
		return new int[] { 5, val2 };
	}

	private int[] straightCheck() {
		int num = hand[0].getFaceValue();
		for (VCard card : hand) {
		//	System.out.println(card);
		//	System.out.println(card.getCardValue());
			// Case: 2,3,4,5,A
			if (num != card.getFaceValue() && hand[0].getFaceValue() == 2) {
				num = 2;
				for (int i = 1; i < hand.length-1; i++) {
					if (num != hand[i].getFaceValue()) {
						return null;
					}
					num++;
				}
				if (hand[4].getFaceValue() == 14){
				return new int[] { 4, 0 };
				}
			} 
			if (num != card.getFaceValue()) {
				return null;
			}
			num--; 
		}
		return new int[] { 4, hand[0].getFaceValue() };
	}

	private int[] kindCheck() {
		int v1 = 0, v2 = 0;
		for (int i = 0; i < hand.length - 1; i++) {
			int checkNum = hand[i].getFaceValue();
			int j = i + 1;
			int count = 1;
			while (j < hand.length && hand[j].getFaceValue() == checkNum) {
				count++;
				j++;
			}
			if (count >= 2) {
				// 1 pair XXXPP
				if (i == 3) {
					v1 = 1;
					v2 = hand[0].getFaceValue() * 400 + hand[1].getFaceValue()
							* 20 + hand[2].getFaceValue() + checkNum * 160000;
					return new int[] { v1, v2 };
				}
				// 1 pair XXPPX
				if (i == 2 && count == 2) {
					v1 = 1;
					v2 = hand[0].getFaceValue() * 400 + hand[1].getFaceValue()
							* 20 + hand[4].getFaceValue() + checkNum * 160000;
					return new int[] { v1, v2 };
				}
				// 3 of a kind XXKKK
				if (i == 2 && count == 3) {
					v1 = 3;
					v2 = hand[0].getFaceValue() * 20 + hand[1].getFaceValue()
							+ checkNum * 160000;
					return new int[] { v1, v2 };
				}
				
				if (i == 1) {
					// 4 of a Kind XKKKK
					if (count == 4) {
						v1 = 7;
						v2 = hand[0].getFaceValue() + checkNum * 160000;
						return new int[] { v1, v2 };
					}
					// 3 of a Kind XKKKX
					if (count == 3) {
						v1 = 3;
						v2 = hand[0].getFaceValue() * 20
								+ hand[4].getFaceValue() + checkNum * 160000;
						return new int[] { v1, v2 };
					}
					// 2 pair XKKPP or 1 pair XKKXX
					if (count == 2) {
						// 2 pair
						if (hand[3].getFaceValue() == hand[4].getFaceValue()) {
							v1 = 2;
							v2 = hand[0].getFaceValue()
									+ hand[4].getFaceValue() * 400 + checkNum
									* 160000;
							return new int[] { v1, v2 };
						}
						// 1 pair XKKXX
						else {
							v1 = 1;
							v2 = hand[0].getFaceValue() * 400
									+ hand[3].getFaceValue() * 20
									+ hand[4].getFaceValue() + checkNum
									* 160000;
							return new int[] { v1, v2 };
						}
					}
				}
				
				else if (i == 0) {
					// 4 of a Kind KKKKX
					if (count == 4) {
						v1 = 7;
						v2 = hand[4].getFaceValue() + checkNum * 160000;
						return new int[] { v1, v2 };
					}
					// 3 of a Kind KKKXX or Full House KKKPP
					if (count == 3) {
						// Full house KKKPP
						if (hand[3].getFaceValue() == hand[4].getFaceValue()) {
							v1 = 6;
							v2 = hand[4].getFaceValue() + checkNum * 160000;
							return new int[] { v1, v2 };
						}
						// 3 of a Kind KKKXX
						else {
							v1 = 3;
							v2 = hand[4].getFaceValue()
									+ hand[3].getFaceValue() * 20 + checkNum
									* 160000;
							return new int[] { v1, v2 };
						}
					}
					// Full House: PPKKK or 2 pair KKPPX or 2 pair KKXPP or 1
					// pair KKXXX 
					else if (count == 2) {
						if (hand[1].getFaceValue() == hand[2].getFaceValue()) {
							// Full House
							if (hand[3].getFaceValue() == hand[4]
									.getFaceValue()) {
								v1 = 6;
								v2 = hand[4].getFaceValue() * 160000 + checkNum;
								return new int[] { v1, v2 };
							}
							// Three of a Kind  KKKXX
							else {
								v1 = 3;
								v2 = hand[4].getFaceValue()
										+ hand[3].getFaceValue() * 20
										+ checkNum * 160000;
								return new int[] { v1, v2 };
							}
						} else {
							// 2 pair KKPPX
							if (hand[2].getFaceValue() == hand[3]
									.getFaceValue()) {
								v1 = 2;
								v2 = hand[4].getFaceValue()
										+ hand[3].getFaceValue() * 400
										+ checkNum * 160000;
								return new int[] { v1, v2 };
							}
							// 2 pair KKXPP
							else if (hand[3].getFaceValue() == hand[4]
									.getFaceValue()) {
								v1 = 2;
								v2 = hand[2].getFaceValue()
										+ hand[4].getFaceValue() * 400
										+ checkNum * 160000;
								return new int[] { v1, v2 };
							}
							
							// 1 pair KKXXX
							else {
								v1 = 1;
								v2 = hand[2].getFaceValue() * 400
										+ hand[3].getFaceValue() * 20
										+ hand[4].getFaceValue() + checkNum
										* 160000;
								return new int[] { v1, v2 };
							}
						}
					}
				}
			}
		}
		return null;
	}

	public String toString() {
		String st = "";
		for (VCard card : hand) {
			st += card.getCardNum() + card.getSuit() + ",";
		}
		return HAND_TYPE[this.getHand()[0]] + ": " + st;
	}

	public static void main(String[] args) {
		int num = 0;
		while (num<100){
		VDeck deck = new VDeck();
		VCard[] cards = new VCard[] {deck.dealCard(),deck.dealCard(),deck.dealCard(),deck.dealCard(),deck.dealCard() };
		VHand hand = new VHand(cards);
		int[] values = hand.getHand();
		System.out.println(hand);
		System.out.println(values[0] + " , " + values[1]);
		num++;
		}
		VCard[] cards = new VCard[] {new VCard(4),new VCard(5),new VCard(6),new VCard(7),new VCard(8) };
		VHand hand = new VHand(cards);
		int[] values = hand.getHand();
		System.out.println(hand);
		System.out.println(values[0] + " , " + values[1]);
	}
}