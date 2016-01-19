import java.util.ArrayList;

/**
 * The Sectoring algorithm, in which each elevator gets one sector of 
 * the building to work calls from. A standard elevator dispatch algorithm.
 */
public class Sectoring implements Algorithm {

	// processes the calls by assigning them to the appropriate elevator; modifies elevators
	public void process(ArrayList<Elevator> elevators, ArrayList<Call> calls) {
		
		for (Call c : calls) {
			Elevator best = this.sectorize(elevators, c);
			best.addCall(c);
		}
	}
	
	// TODO make generic to n floors
	private Elevator sectorize(ArrayList<Elevator> elevators, Call c) {
		int fromFloor = c.getFromFloor();
		if (fromFloor >= 17) {
			return elevators.get(3);
		}
		if (fromFloor >= 12) {
			return elevators.get(2);
		}
		if (fromFloor >= 7) {
			return elevators.get(1);
		}
		if (fromFloor >= 1) {
			return elevators.get(0);
		}
		throw new IllegalArgumentException();
	}

}
