package gameLogic;

import java.io.*;
import java.util.*;

public class Playerhand {
	private List<Card> player_hand = new ArrayList<>();
	public String name;
	
	
	public Playerhand(String temp){
		name = temp;
	}
	
	public void drawCard(Card newcard){
		player_hand.add(newcard);
	}
	
	public int curHandSize() {
		return player_hand.size();
	}
	
	public void printhand(){
		// System.out.println("Player " + name + " hand: ");
		for(int i = 0; i < player_hand.size(); i++){
			player_hand.get(i).printCard();
		}
	}
	
	public Card discard(int index){
		return player_hand.remove(index);
	}
}
