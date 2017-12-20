package sample.poker;

import java.util.Random;

public class VDeck {
	//VCard[] deck;
	private int[] deck;
	private int ptr;
	
	Random random;
	
	static final int NCARDS = 52;
	
	
	
	public VDeck(){
		
		ptr = 0;
		random = new Random();
		reset();
		
	}
	
	public int[] shuffle (){
		int[]d = new int[NCARDS];
		for (int i = 0; i<NCARDS; i++){
			d[i] = i;
		}
		for (int i=0; i<NCARDS-1;i++){
			int swapIndex = i+random.nextInt(NCARDS-i);
	//		System.out.println("Swap Index "+ swapIndex);
			int temp = d[i];
			d[i] =  d[swapIndex];
			d[swapIndex] = temp;
		}
		return d;
	}
		
	public VCard dealCard(){	
		return new VCard (deck[ptr++]);
	}
	
	public boolean hasMore(){
		return ptr < NCARDS;
	}
	public void reset(){
		deck = shuffle();
		ptr = 0;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int i : deck){
			sb.append(new VCard(i).toString() + ", ");
		}
		return sb.toString();
	}
	
	
	
	public static void main(String[] args){
		VDeck deck = new VDeck();	
		System.out.println(deck.toString());
		System.out.println("DEAL");
		System.out.println(deck.dealCard());
		System.out.println(deck.dealCard());
		System.out.println(deck.dealCard());
		System.out.println(deck.dealCard());
		System.out.println(deck.dealCard());
	}
}
