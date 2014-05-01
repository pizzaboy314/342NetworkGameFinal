import java.io.*;
import java.util.*;

public class Card {	
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
	
	public void printCard(){
		if(value == 13){
			// System.out.println("SKIP");
			ClientInterface.c.cardsHistory.append("SKIP\n");
		}
		else if(value == 14){
			 // System.out.println("WILD");
			ClientInterface.c.cardsHistory.append("WILD\n");
		}
		else {
			 // System.out.println(color + "" + value + "");
			ClientInterface.c.cardsHistory.append(color + "" + value + "\n");
		}
	}
	public void printCard2(){
		if(value == 13){
			// System.out.println("SKIP");
			ClientInterface.c.discardHistory.append("SKIP\n");
		}
		else if(value == 14){
			// System.out.println("WILD");
			ClientInterface.c.discardHistory.append("WILD\n");
		}
		else {
			// System.out.println(color + "" + value + "");
			ClientInterface.c.discardHistory.append(color + "" + value + "\n");
		}
	}
}
