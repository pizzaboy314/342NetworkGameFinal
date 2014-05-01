package gameLogic;
import java.io.*;
import java.util.*;

public final class Card {	
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
}
