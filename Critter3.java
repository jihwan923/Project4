package assignment4;

public class Critter3 extends Critter {

	private int direction;
	private int[] moveGenes = new int[4];
	
	public Critter3(){
		moveGenes[0] = 0;
		moveGenes[1] = 2;
		moveGenes[2] = 4;
		moveGenes[3] = 6;
		direction = moveGenes[Critter.getRandomInt(3)];
	}
	@Override
	public String toString(){
		return "3";
	}
	
	@Override
	public void doTimeStep() {
		//other fight critter walks like a rook in chess
		walk(direction);
		direction = moveGenes[Critter.getRandomInt(3)];
		if(getEnergy() > 100){	// reproduces if energy is greater than 100
			otherFightCritter other = new otherFightCritter();
			reproduce(other, direction);
			direction = moveGenes[Critter.getRandomInt(3)];
		}
	}

	@Override
	public boolean fight(String opponent) {
		// this critter only fights critters of other types
		if(opponent.equals("3")){
			run(direction);
			direction = moveGenes[Critter.getRandomInt(3)];
			return false;
		}
		return true;
	}

}
