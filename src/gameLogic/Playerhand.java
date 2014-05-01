import java.io.*;
import java.util.*;

public class Playerhand {
	private List<Card> player_hand = new ArrayList<>();
	private List<Card> phase_hand = new ArrayList<>();
	public String name;
	public int current_phase;
	int phaseTextIndex;
	ClientInterface mygui = ClientInterface.c;
	public boolean istherePhase = false;


	public Playerhand(String temp, int index){
		name = temp;
		current_phase = 0;
		phaseTextIndex = index;
		mygui.phaseText.add(phaseTextIndex, "Phase " + (current_phase + 1) + " for " + name + ":\nNot completed");
		mygui.updatePhaseHistory();
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
	
	public void sortHand(){
		Collections.sort(player_hand, new Comparator<Card>() {
		    @Override
		    public int compare(Card c1, Card c2) {
		        if (c1.getValue() > c2.getValue())
		            return 1;
		        if (c1.getValue() < c2.getValue())
		            return -1;
		        return 0;
		    }
		});
	}
	public void printhand(){
		// System.out.println("Player " + name + " hand: ");
		sortHand();
		mygui.cardsHistory.setText(null);
		for(int i = 0; i < player_hand.size(); i++){
			player_hand.get(i).printCard();
		}
	}
	
	public void printPhasehand(){
		mygui.phaseText.remove(phaseTextIndex);
		String tempPhasestring = "";
		for(int i = 0; i < phase_hand.size(); i++){
			tempPhasestring = tempPhasestring + phase_hand.get(i).getColor() + phase_hand.get(i).getValue() + " ";
		}
		mygui.phaseText.add(phaseTextIndex, "Phase " + (current_phase) + " for " + name + ":\n" + tempPhasestring);
		mygui.updatePhaseHistory();
	}
	
	public Card discard(int index){
		return player_hand.remove(index);
	}
	
	public void removePhaseCards(int cardValue1, int cardValue2, int wildused){
		int currsize = player_hand.size();
		for(int i = (currsize - 1); i >= 0; i--){
			if(player_hand.get(i).getValue() == cardValue1)
				phase_hand.add(player_hand.remove(i));
			else if(player_hand.get(i).getValue() == cardValue2)
				phase_hand.add(player_hand.remove(i));
			else if(player_hand.get(i).getValue() == 14 && wildused > 0)
				phase_hand.add(player_hand.remove(i));
		}
		
		printhand();
		printPhasehand();
	}

	public boolean phaseCheck(){
		if(current_phase == 0){
			int threeOfaKind = 1;
			int set = 0, cardValue1 = 0, cardValue2 = 0;
			int first_set = 0, wild_index = 0, wild_temp = 0 ,wild_used = 0;
			boolean wild = false;
			for(int i = 0; i < player_hand.size(); i++){
				if(player_hand.get(i).getValue() != 14 && player_hand.get(i).getValue() != 13 && player_hand.get(i).getValue() != first_set){
					for(int j = 0; j < player_hand.size(); j++ ){
						if(player_hand.get(i).getValue() == player_hand.get(j).getValue() && i != j)
							threeOfaKind++;
						else if(player_hand.get(j).getValue() == 14){
							if(wild_temp != j){
								wild = true;
							wild_index = j;
							}
						}
						System.out.println(player_hand.get(i).getValue() + " " + threeOfaKind);
						
					}
					if(threeOfaKind >= 3){
						set++;
						if(first_set == 0)
							first_set = player_hand.get(i).getValue();
						wild = false;
						System.out.println("We got a set");
					}
					else if(threeOfaKind == 2 && wild == true){
						wild = ClientInterface.c.useWild(1, player_hand.get(i).getValue());
						if(wild == true){
							set++;
							if(first_set == 0)
								first_set = player_hand.get(i).getValue();
							wild_temp = wild_index;
							wild_index = 0;
							wild_used++;
							wild = false;
							System.out.println("We got a set");
						}
						else
							wild = false;
					}
					if(set == 2){
						current_phase++;
						cardValue1 = first_set;
						cardValue2 = player_hand.get(i).getValue();
						System.out.println("My cards are: " + cardValue1 + " " + cardValue2);
						mygui.phaseCheck.setEnabled(false);
						removePhaseCards(cardValue1, cardValue2, wild_used);
						return true;
					}
					threeOfaKind = 1;
				}
			}
		}
		else if(current_phase == 1){
			int threeOfaKind = 1;
			int set = 0, cardValue1 = 0, cardValue2 = 0;
			int first_set = 0, wild_index = 0, wild_temp = 0, wild_used = 0;
			boolean wild = false;
			for(int i = 0; i < player_hand.size(); i++){
				if(player_hand.get(i).getValue() != 14 || player_hand.get(i).getValue() != 13 || player_hand.get(i).getValue() != first_set){
					for(int j = 0; j < player_hand.size(); j++ ){
						if(player_hand.get(i).getValue() == player_hand.get(j).getValue() && i != j)
							threeOfaKind++;
						else if(player_hand.get(j).getValue() == 14){
							if(wild_temp != j){
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
							wild_temp = wild_index;
							wild_index = 0;
							wild_used++;
							wild = false;
							System.out.println("We got a set");
						}
						else
							wild = false;
					}
					if(set == 1){
						cardValue1 = i;
						break;
					}
					threeOfaKind = 1;
				}
			}
			System.out.println("We broke out of the loop");

		}
		
		return false;
	}
}
