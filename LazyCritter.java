package assignment4;

public class LazyCritter extends Critter {
	private int randomDir;
	public String toString(){
		return "L";
	}
	@Override
	public void doTimeStep() {
		int randomInt = Critter.getRandomInt(9);
		randomDir = Critter.getRandomInt(7);
		if(randomInt <= 7){
			return;
		}
		if(randomInt == 8){
			run(randomDir);
		}
		if(randomInt == 9){
			walk(randomDir);
		}

	}

	@Override
	public boolean fight(String opponent) {
		randomDir = Critter.getRandomInt(7);
		walk(randomDir);
		return false;
	}

}
