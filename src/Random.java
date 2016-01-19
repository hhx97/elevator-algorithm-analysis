import java.util.ArrayList;

/**
 * An algorithm that assigns each call to a random elevator (benchmark)
 */
public class Random implements Algorithm {

	// processes the calls by assigning them to the appropriate elevator; modifies elevators
	public void process(ArrayList<Elevator> elevators, ArrayList<Call> calls) {
		for (Call c : calls) {
			elevators.get((int)(Math.random() * Building.elevatorCount)).addCall(c);
		}
	}

}
