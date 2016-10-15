/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	public Critter(){ // constructor for Critter
		energy = Params.start_energy;
		x_coord = getRandomInt(Params.world_width - 1);
		y_coord = getRandomInt(Params.world_height - 1);
	}
	
	protected int wrapX(int pos){
		if (pos < 0){
			return Params.world_width - 1;
		}
		else if (pos == Params.world_width){
			return 0;
		}
		else{
			return pos;
		}
	}
	
	protected int wrapY(int pos){
		if (pos < 0){
			return Params.world_height - 1;
		}
		else if (pos == Params.world_height){
			return 0;
		}
		else{
			return pos;
		}
	}
	
	protected int newX(int direction){
		switch(direction){
		case 0: return wrapX(x_coord + 1);
		case 1: return wrapX(x_coord + 1);
		case 7: return wrapX(x_coord + 1);
		case 3: return wrapX(x_coord - 1);
		case 4: return wrapX(x_coord - 1);
		case 5: return wrapX(x_coord - 1);
		}
		return x_coord; // for directions 2 and 6
	}
	
	protected int newY(int direction){
		switch(direction){
		case 5: return wrapY(y_coord + 1);
		case 6: return wrapY(y_coord + 1);
		case 7: return wrapY(y_coord + 1);
		case 1: return wrapY(y_coord - 1);
		case 2: return wrapY(y_coord - 1);
		case 3: return wrapY(y_coord - 1);
		}
		return y_coord; // for direction 0 and 4
	}
	
	protected final void walk(int direction) {
		x_coord = newX(direction);
		y_coord = newY(direction);
		energy = energy - Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		walk(direction);
		walk(direction);
		energy = energy - Params.run_energy_cost + Params.walk_energy_cost + Params.walk_energy_cost;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			String packageClass = myPackage + "." + critter_class_name;
			Class critterClass = Class.forName(packageClass);
			Object newCritter = critterClass.newInstance();
			
			if (!(newCritter instanceof Critter)){
				throw new InvalidCritterException(critter_class_name);
			}
			
			CritterWorld.critterCollection.add((Critter)newCritter); // add to the critter collection
		}
		catch(ClassNotFoundException c){
			throw new InvalidCritterException(critter_class_name);
		}
		catch(InstantiationException i){
			throw new InvalidCritterException(critter_class_name);
		}
		catch(IllegalAccessException a){
			throw new InvalidCritterException(critter_class_name);
		}
		catch(Exception e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			String packageClass = myPackage + "." + critter_class_name;
			Class critterClass = Class.forName(packageClass);
			Object newCritter = critterClass.newInstance();
			if (!(newCritter instanceof Critter)){
				throw new InvalidCritterException(critter_class_name);
			}
			
			for(Critter c: CritterWorld.critterCollection){
				if (critterClass.isAssignableFrom(c.getClass())){
					result.add(c);
				}
			}
		}
		catch(ClassNotFoundException c){
			
		}
		catch(InstantiationException i){
			
		}
		catch(IllegalAccessException a){
			
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected List<Critter> getBabies() {
			return babies;
		}
	}
	
	

	public static void worldTimeStep() {
		for(Critter c: CritterWorld.critterCollection){ // invoke doTimeStep for all critters
			c.doTimeStep();
		}
		
		for(Critter c: CritterWorld.critterCollection){ // apply rest energy cost to all critters
			c.energy = c.energy - Params.rest_energy_cost;
		}
		
		for(int i = 0; i < CritterWorld.critterCollection.size(); i++){ // remove all dead critters
			Critter c = CritterWorld.critterCollection.get(i);
			if (c.energy <= 0){
				CritterWorld.critterCollection.remove(i);
				i -= 1;
			}
			
			if (CritterWorld.critterCollection.size() == 0){
				break;
			}
		}
		
	}
	
	public static void displayWorld() {
		String[][] critterWorld = new String[Params.world_width + 2][Params.world_height + 2];
		
		for(int i = 0; i < (Params.world_height + 2); i++){
			for(int j = 0; j < (Params.world_width + 2); j++){
				critterWorld[j][i] = " ";
			}
		}
		
		critterWorld[0][0] = critterWorld[0][Params.world_height + 1] = "+";
		critterWorld[Params.world_width + 1][Params.world_height + 1] = critterWorld[Params.world_width + 1][0] = "+\n";
		
		for (int w = 1; w < (Params.world_width + 1); w++){
			critterWorld[w][0] = "-";
			critterWorld[w][Params.world_height + 1] = "-";
		}
		
		for (int h = 1; h < (Params.world_height + 1); h++){
			critterWorld[0][h] = "|";
			critterWorld[Params.world_width + 1][h] = "|\n";
		}
		
		for(Critter c: CritterWorld.critterCollection){ // place all the critters to the world
			critterWorld[c.x_coord + 1][c.y_coord + 1] = c.toString(); // shift them so they fit inside the world 'box'
		}
		
		for(int i = 0; i < (Params.world_height + 2); i++){
			for(int j = 0; j < (Params.world_width + 2); j++){
				System.out.print(critterWorld[j][i]);
			}
		}
	}
}
