import java.io.*;
import java.util.*;

public class CardPile {
	public List<Card> pile = new ArrayList<>();
	
	private int numOfCards;
	private boolean discard;
	
	public CardPile() {
		numOfCards = 0;
		discard = true;
	}
	
	public CardPile(boolean shuffle){
		numOfCards = 0;
		ColorCardsInsert('R');
		ColorCardsInsert('O');
		ColorCardsInsert('Y');
		ColorCardsInsert('G');
		pile.add(new Card(13, 'S'));
		pile.add(new Card(13, 'S'));
		pile.add(new Card(13, 'S'));
		pile.add(new Card(13, 'S'));
		numOfCards += 4;
		for(int i = 0; i < 8; i++){
			pile.add(new Card(14, 'W'));
			numOfCards++;
		}	
		
		if(shuffle){
			Shuffle_Pile();
		}
		
		discard = false;
	}
	
	private void ColorCardsInsert(char color){
		for(int i = 1; i <= 12; i++){
			pile.add(new Card(i, color));
			numOfCards++;
		}
		for(int i = 1; i <= 12; i++){
			pile.add(new Card(i, color));
			numOfCards++;
		}
	}
	
	private void Shuffle_Pile(){
		Collections.shuffle(pile);
	}
	
	public void PilePrint() {
		for(int i = 0; i < numOfCards; i++){
			pile.get(i).printCard2();
		}
		System.out.print("\n");
	}
	
	public int cards_left(){
		return numOfCards;
	}
	
	public Card drawCard() {
		if(numOfCards == 0){
			System.out.println("Cannot draw more cards.");
			return null;
		}
		int card = numOfCards - 1;
		numOfCards--;
		return pile.remove(card);
	}
	
	public void insertCard(Card insert){
		pile.add(insert);
		numOfCards++;
	}
	
	public void printTopCard(){
		pile.get(numOfCards - 1 ).printCard();
	}
/*
	public static void main(String [] args){

		CardPile deck = new CardPile(true);
		CardPile discard = new CardPile();
		//deck.PilePrint();
		deck.drawCard();
		
		Playerhand player1 = new Playerhand("player1");
		Playerhand player2 = new Playerhand("player2");
		
		for(int i = 0; i < 10; i++){
			player1.drawCard(deck.drawCard());
			player2.drawCard(deck.drawCard());
		}
		
		discard.insertCard(deck.drawCard());
		
		System.out.print("Visible card: ");
		discard.printTopCard();
		
		player1.printhand();
		// player2.printhand();
		
		
	}
	*/
}
