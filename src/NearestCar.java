import java.util.ArrayList;

/**
 * The Nearest Car algorithm, a standard elevator dispatch algorithm.
 */
public class NearestCar implements Algorithm {

	// processes the calls by assigning them to the appropriate elevator; modifies elevators
	public void process(ArrayList<Elevator> elevators, ArrayList<Call> calls) {
		for (Call c : calls) {
			Elevator bestElevator = null;
			int bestFS = 0;
			for (Elevator e : elevators) {
				int fs = this.calculateFigureOfSuitability(e, c);
				if (fs > bestFS) {
					bestFS = fs;
					bestElevator = e;
				}
			}
			bestElevator.addCall(c);
		}
	}
	
	/**
	 * Calculates figure of suitability based on the Nearest Car standard algorithm
	 * @param e the elevator
	 * @param c the call
	 * @return the figure of suitability (fs)
	 */
	private int calculateFigureOfSuitability(Elevator e, Call c) {
		int N = Building.floors - 1;
		int d = Math.abs(e.getFloor() - c.getFromFloor());
		
		boolean movingTowardsOrAt = this.movingTowardsOrAt(e, c);
		if (movingTowardsOrAt && e.getDirection() == c.direction()) {
			return N + 2 - d;
		} else if (movingTowardsOrAt && e.getDirection() != c.direction()){
			return N + 1 - d;
		} else if (!movingTowardsOrAt) {
			return 1;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private boolean movingTowardsOrAt(Elevator e, Call c) {
		return (e.getDirection() == 1 && e.getFloor() >= c.getFromFloor() ||
				e.getDirection() == -1 && e.getFloor() <= c.getFromFloor());
	}
}
