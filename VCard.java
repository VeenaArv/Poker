package sample.poker;

public class VCard {
	public int cardValue;
	public String[] SUIT  = new String[]{"S","H", "D", "C"};

	public VCard(int cv){
		cardValue = cv;
	}
	
	public String getSuit(){
		return SUIT[cardValue/13];
	}
	public String getCardNum(){
		String[] faces = new String[]{"J","Q","K","A"};
		
		int faceValue = getFaceValue();
		if (faceValue >= 11){
			return faces[faceValue - 11];
		}	else{
			return String.valueOf(faceValue);
		}
		 
	}
	
	
	// starts from 1 
	public int getFaceValue(){
		return cardValue%13+2;
	}
	
	
	public String toString (){
		return getCardNum() + getSuit();
	}
	public static void main (String[] args){
		VCard card = new VCard(9);
		System.out.println(card);
		card = new VCard(9+13);
		System.out.println(card);
		card = new VCard(9+26);
		System.out.println(card);
		card = new VCard(9+13+26);
		System.out.println(card);
		card = new VCard(15);
		System.out.println(card);
		card = new VCard(51);
		System.out.println(card);
		
		for (int i=0;i<52;i++) {
			card = new VCard(i);
			System.out.println(i+"->"+card+"; fv="+card.getFaceValue());
		}
	}
}
