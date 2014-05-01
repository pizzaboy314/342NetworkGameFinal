package gameLogic;
import java.io.*;
import java.util.*;

public final class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int value;
	private char color;
	
	public Card(int a, char b){
		value = a;
		color = b;
	}
	
	public int getValue(){
		return value;
	}
	
	public char getColor(){
		return color;
	}
	
	public void printCard(ClientInterface c) {
		if (value == 13) {
			// System.out.println("SKIP");
			c.cardsHistory.append("SKIP\n");
		} else if (value == 14) {
			// System.out.println("WILD");
			c.cardsHistory.append("WILD\n");
		} else {
			// System.out.println(color + "" + value + "");
			c.cardsHistory.append(color + "" + value + "\n");
		}
	}
	
	public String printCard2() {
		if (value == 13) {
			// System.out.println("SKIP");
			//c.discardHistory.append("SKIP\n");
			return "SKIP";
		} else if (value == 14) {
			// System.out.println("WILD");
			//c.discardHistory.append("WILD\n");
			return "WILD";
		} else {
			// System.out.println(color + "" + value + "");
			//c.discardHistory.append(color + "" + value + "\n");
			return color + "" + value;
		}
	}
}
