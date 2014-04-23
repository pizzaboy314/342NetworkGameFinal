package gameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public final class Card extends Rectangle{
	public final char suit;
	public final int rank;//let ace be 14
	private static int size;

	public Card(int s, int r) {
		suit = s == 0?'S':s == 1?'H':s == 2?'C':'D';
		rank = r;
		size = 50;
		width = size;
		height = size * 2;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.draw3DRect(this.x, this.y, this.width, this.height, true);
	}

}
