package assignment4;

public class Cannibal extends Critter {
	
	@Override
	public String toString() { return "N"; }
	
	private int[] moveGenes = new int[4];
	private int dir;
	
	public Cannibal() { // cannibal only moves like a chess equivalent of bishop (diagnoal movements)
		moveGenes[0] = 1;
		moveGenes[1] = 3;
		moveGenes[2] = 5;
		moveGenes[3] = 7;
		dir = moveGenes[Critter.getRandomInt(3)];
	}
	
	@Override
	public void doTimeStep() {
		int moveDecision = Critter.getRandomInt(2);
		switch(moveDecision){
			case 0:
				run(dir);
				break;
			case 1:
				walk(dir);
				break;
			case 2:
				break;
		}
		
		Cannibal child = new Cannibal();
		reproduce(child, Critter.getRandomInt(8));
		
		dir = moveGenes[Critter.getRandomInt(3)];
	}

	@Override
	public boolean fight(String opponent) {
		// fights algae or its own type when it has sufficient energy
		Algae alg = new Algae();
		String algaeName = alg.getClass().getName();
		String cannibalName = this.getClass().getName();
		if ((opponent.equals(algaeName) || opponent.equals(cannibalName)) && getEnergy() > 20){ 
			return true;
		}
		run(dir); // else, it "tries" to run away
		return false;
	}

}
