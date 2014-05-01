import java.io.*;
import java.util.*;

public class Playerhand {
	private List<Card> player_hand = new ArrayList<>();
	public String name;
	public int current_phase;


	public Playerhand(String temp){
		name = temp;
		current_phase = 0;
	}
	
	public void drawCard(Card newcard){
		player_hand.add(newcard);
	}
	
	// returns a copy of the card at the given index
	public Card getCard(int index) {
		Card tempCard = new Card(player_hand.get(index).getValue(), player_hand.get(index).getColor());
		return tempCard;
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

	public boolean phaseCheck(){
		if(current_phase == 0){
			int threeOfaKind = 1;
			int set = 0, cardValue1 = 0, cardValue2 = 0;
			int first_set = 0, wild_index = 0, wild_used = 0;
			boolean wild = false;
			for(int i = 0; i < player_hand.size(); i++){
				if(player_hand.get(i).getValue() != 14 || player_hand.get(i).getValue() != 13 || player_hand.get(i).getValue() != first_set){
					for(int j = 0; j < player_hand.size(); j++ ){
						if(player_hand.get(i).getValue() == player_hand.get(j).getValue() && i != j)
							threeOfaKind++;
						else if(player_hand.get(j).getValue() == 14){
							if(wild_used != j){
								wild = true;
							wild_index = j;
							}
						}
						System.out.println(player_hand.get(i).getValue() + " " + threeOfaKind);
						
					}
					if(threeOfaKind >= 3){
						set++;
						first_set = player_hand.get(i).getValue();
						wild = false;
						System.out.println("We got a set");
					}
					else if(threeOfaKind == 2 && wild == true){
						wild = ClientInterface.c.useWild(1, player_hand.get(i).getValue());
						if(wild == true){
							set++;
							first_set = player_hand.get(i).getValue();
							wild_used = wild_index;
							wild_index = 0;
							wild = false;
							System.out.println("We got a set");
						}
						else
							wild = false;
					}
					if(set == 2){
						current_phase++;
						cardValue1 = first_set;
						cardValue2 = i;
						return true;
					}
					threeOfaKind = 1;
				}
				
			}
		}
		return false;
	}
}
