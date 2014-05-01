package gameLogic;
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
}
