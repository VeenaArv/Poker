package sample.poker;

public class VPoker {
	VDeck deck;
	VPlayer[] players;
	int playerCount;
	VCard[] communityCards;
	VHand bestPlayerHand;
	VPlayer winner;
	String[] HAND_TYPE = new String[]{"High Card","One Pair","Two Pair","Three of a Kind","Straight","Flush",
			"Full House","Four of a Kind", "Straight Flush"
	};
	public VPoker(int playerCount){
		deck = new VDeck();
		this.playerCount = playerCount;
		communityCards = new VCard[5];
		dealCards();
		
	}
	private void dealCards(){
		players = new VPlayer[playerCount];
		for (int i = 0; i<playerCount;i++){
			players[i] = new VPlayer(deck.dealCard());
		}
		for (int i = 0; i<playerCount;i++){
		VCard c1 = players[i].getFirstCard();
		players[i] = new VPlayer(c1,deck.dealCard());
		}
		deck.dealCard();
		communityCards[0] = deck.dealCard();
		communityCards[1] = deck.dealCard();
		communityCards[2] = deck.dealCard();
		deck.dealCard();
		communityCards[3] = deck.dealCard();
		deck.dealCard();
		communityCards[4] = deck.dealCard();
	}
	public VPlayer getWinner(){
		if (winner != null){
			return winner;
		}
		else {
			getBestHand();
			return winner;
		}
	}
	public String getBestHand(){
		int[] bestHandValue = new int[]{0,0};
		for (VPlayer p : players){
			int[] handValue = getBestPlayerHand(p);
			if ((handValue[0] > bestHandValue[0]) || (handValue[0] == bestHandValue[0] && handValue[1] > bestHandValue[1])){
			//	System.out.println(handValue[0]);
				bestHandValue = handValue;
				winner = p;
			}
			
		}
		getBestPlayerHand(winner);
		return winner.toString() + " " +  bestPlayerHand.toString();
	}
	public VPlayer[] getPlayers(){
		return players;
	}
	public VCard[] getCommunityCards(){
		return communityCards;
	}
	private int[] getBestPlayerHand(VPlayer p){
		bestPlayerHand = new VHand(communityCards);
		int[] bestHandValue = bestPlayerHand.getHand();
		int num = 1;
		while (num <= 6){
		for (int i= num+1; i<= 7; i++){
			VHand testHand = new VHand(cardSeq(p,num,i));
			int[] testHandValue = testHand.getHand();
	//		System.out.println("hand " + bestPlayerHand);
			if ((testHandValue[0] > bestHandValue[0]) || (testHandValue[0] == bestHandValue[0] && testHandValue[1] > bestHandValue[1])){
				bestHandValue = testHandValue;
				bestPlayerHand = testHand;
			}
		} 
			num++; 
		} 
			return bestHandValue;
		}
	// creates a sequence by picking 5 of 7 cards thats isnt n1 or n2 
	private VCard[] cardSeq(VPlayer p,int n1, int n2){
		VCard[] arr = new VCard[5];
		int count = 0;
		for (int i = 1; i<6;i++){
			if (i != n1 && i != n2){
				arr[count] = communityCards[i-1];
				count++;
			} 
		}
		if (6 != n1 && 6 != n2){
			arr[count] = p.getFirstCard();
			count++;
		}
		if (7 != n1 && 7 != n2){
			arr[count] = p.getSecondCard();
		}
		return arr;
	}
	public static void main(String[] args){
		VPoker game = new VPoker(5);
		VPlayer[] p =game.getPlayers();
		System.out.print("Community Cards: ");
		for (VCard card : game.getCommunityCards()){
			System.out.print(card + ", ");
		}
		System.out.println();
		for (int i =0; i < p.length; i++){
		System.out.print("Player "+ (i+1) + " -> " + p[i] + " | ");
		for (VCard card : game.getCommunityCards()){
			System.out.print(card + ", ");
		}
		System.out.println();
		game.getBestPlayerHand(p[i]);
		System.out.println("Hand: " + game.bestPlayerHand);
		}
		System.out.println(game.getBestHand());
	}
}
