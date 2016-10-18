package assignment4;

public class Critter4 extends Critter {
	private int randomDir;
	public String toString(){
		return "4";
	}
	@Override
	public void doTimeStep() {
		int randomInt = Critter.getRandomInt(9);
		randomDir = Critter.getRandomInt(7);
		if(randomInt <= 7){
		}
		else if(randomInt == 8){
			run(randomDir);
			randomDir = Critter.getRandomInt(7);
		}
		else if(randomInt == 9){
			walk(randomDir);
			randomDir = Critter.getRandomInt(7);
		}
		if(getEnergy() > 200){	// only reproduces if energy is > 200
			lazyCritter lazycritter = new lazyCritter();
			reproduce(lazycritter, randomDir);
			randomDir = Critter.getRandomInt(7);
		}
	}

	@Override
	public boolean fight(String opponent) {
		Algae alg = new Algae();
		String algaeName = alg.getClass().getName();
		if(opponent.equals(algaeName)){	// fights algae
			return true;
		}
		walk(randomDir);			// walks away from any other fight
		randomDir = Critter.getRandomInt(7);
		return false;
	}

}