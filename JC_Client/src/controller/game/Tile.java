package controller.game;

import java.io.Serializable;
import java.util.Random;
/**
 * <code><b>Tile</b></code> keep the value for the tile's position,and some other tile operations;
 * 
 * @author 
 */

public class Tile implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row = 0;
	private int col = 0;
	private int x;
	private int y;
	private Color color;
	private Type t;
	private boolean willDelete = false;
	private boolean selected;
	public int fallDistance = 0;
	private boolean willDrop = false;

	public boolean isWillDrop() {
		return willDrop;
	}

	public void setWillDrop(boolean willDrop) {
		this.willDrop = willDrop;
	}

	public boolean isWillDelete() {
		return willDelete;
	}

	public void setWillDelete(boolean willDelete) {
		this.willDelete = willDelete;
	}

	public Tile clone() {
		Tile newt = new Tile(this.row, this.col, this.color);
		newt.fallDistance = this.fallDistance;
		newt.selected = this.selected;
		newt.t = this.t;
		newt.willDelete = this.willDelete;
		newt.willDrop = this.willDrop;
		return newt;
	}

	public Tile(int row, int col, Color color) {
		this.row = row;
		this.col = col;
		this.color = color;
		x = col * 60 + 215;
		y = row * 60 + 60;
		fallDistance = 0;
		selected = false;
		t = Type.normal;
		willDelete = false;
	}

	public static Tile getRandomTile(int row, int col) {
		Random rand = new Random();
		int tileNum = rand.nextInt(7);
		Tile tile = null;
		switch (tileNum) {
		case 0:
			tile = new Tile(row, col, Color.picture1);
			break;
		case 1:
			tile = new Tile(row, col, Color.picture2);
			break;
		case 2:
			tile = new Tile(row, col, Color.picture3);
			break;
		case 3:
			tile = new Tile(row, col, Color.picture4);
			break;
		case 4:
			tile = new Tile(row, col, Color.picture5);
			break;
		case 5:
			tile = new Tile(row, col, Color.picture6);
			break;
		case 6:
			tile = new Tile(row, col, Color.picture7);
			break;
		}
		return tile;

	}

	public void setColor(Color c) {
		this.color = c;
	}

	public void settype(Type t) {
		this.t = t;
	}

	public Color getColor() {
		return this.color;
	}

	public Type getType() {
		return this.t;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
		y = row * 60 + 60;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
		x = col * 60 + 215;

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.col = (x - 215) / 60;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.row = (y - 60) / 60;
	}

	public static Color getRandColor() {
		Random rand = new Random();
		int tileNum = rand.nextInt(7);
		switch (tileNum) {
		case 0:
			return Color.picture1;

		case 1:
			return Color.picture2;
		case 2:
			return Color.picture3;
		case 3:
			return Color.picture4;
		case 4:
			return Color.picture5;
		case 5:
			return Color.picture6;
		case 6:
			return Color.picture7;
		}
		return Color.picture1;

	}

	public boolean isNeighbor(Tile otherTile) {
		if (Math.abs(row - otherTile.row) == 1
				&& Math.abs(getCol() - otherTile.getCol()) == 0) {
			return true;
		} else if (Math.abs(row - otherTile.row) == 0
				&& Math.abs(getCol() - otherTile.getCol()) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void moveY(int direction, int step) {
		y += direction * step;
	}

	public void moveX(int direction, int step) {
		x += direction * step;
	}

	public void reset() {
		this.fallDistance = 0;
		// this.selected = false;
		this.willDelete = false;
		this.willDrop = false;
	}
	
	public static Tile getRandomPosition(){
		Tile t   = Tile.getRandomTile(0, 0);
		Random r = new Random();
		int row = r.nextInt(9);
		int col = r.nextInt(9);
		t.setRow(row);
		t.setCol(col);
		
		return t;
	}

}
