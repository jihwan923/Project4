package assignment4;

import java.util.ArrayList;
import java.util.Collection;

public class CritterWorld {
	public static ArrayList<Critter> critterCollection = new ArrayList<Critter>();
	public String[][] world;
	private int height;
	private int width;
	final public static ArrayList<String> critterNames = new ArrayList<String>();
	/**
	 * CritterWorld Constructor
	 * Initializes variables and creates the board
	 */
	CritterWorld(){
		height = Params.world_height;
		width = Params.world_width;
		world = new String[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				world[i][j] = "*";
			}
		}
		critterNames.add("Algae");
		critterNames.add("Craig");
	}
	/**
	 * displayWorld Method
	 * print 2D grid to System.out
	 */
	void displayWorld(){
		for(int i = 0; i < height; i++){
			String blank = "";
			for(int j = 0; j < width; j++){
				System.out.print(blank + "*");
				blank = " ";
			}
			System.out.println();
		}
	}
}	
