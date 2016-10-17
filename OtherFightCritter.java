package assignment4;

public class OtherFightCritter extends Critter {

	private int direction;
	private int[] moveGenes = new int[4];
	
	public OtherFightCritter(){
		moveGenes[0] = 0;
		moveGenes[1] = 2;
		moveGenes[2] = 4;
		moveGenes[3] = 6;
		direction = moveGenes[Critter.getRandomInt(3)];
	}
	@Override
	public String toString(){
		return "A";
	}
	
	@Override
	public void doTimeStep() {
		//other fight critter walks like a rook in chess
		walk(direction);
		direction = moveGenes[Critter.getRandomInt(3)];

	}

	@Override
	public boolean fight(String opponent) {
		// this critter only fights critters of other types
		if(this.getClass().getName().equals(opponent)){
			run(direction);
			direction = moveGenes[Critter.getRandomInt(3)];
			return false;
		}
		return true;
	}

}
