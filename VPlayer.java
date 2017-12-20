package sample.poker;

public class VPlayer {
	private VCard card1, card2;
	public VPlayer(VCard c1){
		card1 = c1;
	}
	public VPlayer(VCard c1, VCard c2){
		card1 = c1;
		card2 = c2;
	}
	public VCard getFirstCard(){
		return card1;
	}
	public VCard getSecondCard(){
		return card2;
	}
	public String toString (){
		 return card1.toString() + ", " + card2.toString();
	}
}
	

