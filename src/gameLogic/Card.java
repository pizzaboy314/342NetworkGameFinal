//package phase10game;
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
			System.out.println("SKIP");
			ClientInterface.c.cardsHistory.append("SKIP");
			ClientInterface.c.cardsHistory.append("\n");
		}
		else if(value == 14){
			System.out.println("WILD");
			ClientInterface.c.cardsHistory.append("WILD");
			ClientInterface.c.cardsHistory.append("\n");
		}
		else {
			System.out.println(color + "" + value + "");
			ClientInterface.c.cardsHistory.append(color + "" + value + "");
			ClientInterface.c.cardsHistory.append("\n");
		}
	}
}
