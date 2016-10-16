package assignment4;

public class ImbecileCritter extends Critter {

	@Override
	public String toString() { return "S"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int encounterGene;
	private int dir;
	
	public ImbecileCritter() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		encounterGene = 90;
		dir = Critter.getRandomInt(8);
	}
	
	@Override
	public void doTimeStep() {
		// stupid critter always wastes energy by walking or running twice.
		int moveDecision = Critter.getRandomInt(2);
		switch(moveDecision){
			case 0:
				run(dir);
				run(dir);
				break;
			case 1:
				walk(dir);
				walk(dir);
				break;
			case 2:
				break;
		}
		
		if (getEnergy() > Params.min_reproduce_energy) {
			ImbecileCritter child = new ImbecileCritter();
			child.encounterGene = this.encounterGene;
			int e = Critter.getRandomInt(99);
			if (e < 10){
				child.encounterGene -= 1;
				if (child.encounterGene < 0){
					child.encounterGene = 0;
				}
			}
			
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	@Override
	public boolean fight(String opponent) {
		Algae alg = new Algae();
		String algaeName = alg.getClass().getName();
		int encounterChoice = Critter.getRandomInt(99);
		
		// majority of the time, this imbecile critter thinks algae is dangerous
		if (opponent.equals(algaeName) && (encounterChoice < encounterGene)){  
			run(dir); // it wastes more energy by running
			run(dir); // AND wastes another energy by running again
			return false;
		}
		return true; // it fights other critters
	}

	
	public static void runStats(java.util.List<Critter> imbs) {
		int total_gene = 0;

		for (Object obj : imbs) {
			ImbecileCritter c = (ImbecileCritter) obj;
			total_gene += c.encounterGene;
		}
		System.out.print("" + imbs.size() + " total Imbeciles    ");
		if (imbs.size() > 0){
			System.out.print("" + total_gene / (imbs.size()) + "% avoid Algae ");
		}
		System.out.println();
	}
}
