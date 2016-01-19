import java.util.ArrayList;

/**
 * Represents a single elevator and its movement.
 */
public class Elevator {
	
	private int floor; // the current floor of the elevator
	private int direction; // 1 for up, -1 for down
	private int time; // time spent in the system (seconds) (includes time in transit and time waiting for passengers to get on)	
	private ArrayList<Call> calls; // the calls that the elevator must service
	
	public Elevator(int floor, int direction) {
		this.floor = floor;
		this.direction = direction;
		this.time = 0;
		this.calls = new ArrayList<Call>();
	}
	
	public void addCall(Call c) {
		calls.add(c);
	}
	
	/**
	 * Step in the direction that the elevator is supposed to based on the standard movement pattern.
	 * 
	 * Basic: First, stop on floor if needed. Then, move a step if needed (Y).
	 * 
	 * When to stop on floor: If 
	 * 		A: current floor matches ToFloor of any calls currently being serviced
	 *   OR B: (current floor matches FromFloor of any unserviced calls AND call in same direction as elevator)
	 *   OR C: (current floor matches FromFloor of any unserviced calls AND call not in same direction as elevator AND there are no more calls needing to be serviced in the direction we're going)
	 * 
	 * then stop on the floor, adding extra time.
	 * Also:
	 *     If A, change those calls states' to "has been serviced".
	 *     If B or C, change those calls states' to "currently being serviced".
	 *     
	 * When/how to move a step:
	 * 		If all calls have been serviced, do not move, and exit method.
	 * 		If there are no more calls needing to be serviced in the direction we're going, change direction.
	 * 		Move a step in the current direction.
	 */
	public void step() {
		// stop if needed
		
		ArrayList<Call> aMatches = new ArrayList<Call>();
		for (Call c : this.getCallsOfState(1)) {
			if (floor == c.getToFloor()) {
				aMatches.add(c);
			}
		}
		
		ArrayList<Call> bMatches = new ArrayList<Call>();
		for (Call c : this.getCallsOfState(0)) {
			if (floor == c.getFromFloor() && direction == c.direction()) {
				bMatches.add(c);
			}
		}
		
		ArrayList<Call> cMatches = new ArrayList<Call>();
		for (Call c : this.getCallsOfState(0)) {
			if (floor == c.getFromFloor() && direction != c.direction() && this.noMoreCallsInCurrentDirection()) {
				cMatches.add(c);
			}
		}
		
		if (!(aMatches.isEmpty() && bMatches.isEmpty() && cMatches.isEmpty())) {
			time += 10;
			
			if (!aMatches.isEmpty()) {
				for (Call c : aMatches) {
					c.setState(2);
				}
			}
			if (!bMatches.isEmpty()) {
				for (Call c : bMatches) {
					c.setState(1);
				}
			}
			if (!cMatches.isEmpty()) {
				for (Call c : cMatches) {
					c.setState(1);
				}
			}
		}
		
		// move if needed
		
		if (this.isDone()) {
			return;
		}
		if (this.noMoreCallsInCurrentDirection()) {
			direction *= -1;
		}
		floor += direction;
		time += 2;
	}
	
	/**
	 * @param state 0 = unserviced, 1 = being serviced, 2 = have been serviced
	 * @return an arraylist containing all such calls
	 */
	private ArrayList<Call> getCallsOfState(int state) {
		ArrayList<Call> callsOfGivenState = new ArrayList<Call>();
		for (Call c : calls) {
			if (c.getState() == state) {
				callsOfGivenState.add(c);
			}
		}
		return callsOfGivenState;
	}
	
	/**
	 * @return true if there are no more ToFloor matches in calls being 
	 * serviced strictly after the current floor (given the current direction)
	 * and there are no more FromFloor matches in unserviced calls 
	 * strictly after the current floor (given the current direction)
	 */
	private boolean noMoreCallsInCurrentDirection() {
		for (Call c : calls) {
			if (c.getState() == 1) { // currently being serviced
				if (direction == 1) {
					if (c.getToFloor() > floor) {
						return false;
					}
				}
				if (direction == -1) {
					if (c.getToFloor() < floor) {
						return false;
					}
				}
			}
			if (c.getState() == 0) { // waiting to be serviced
				if (direction == 1) {
					if (c.getFromFloor() > floor) {
						return false;
					}
				}
				if (direction == -1) {
					if (c.getFromFloor() < floor) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * @return whether the elevator has anything left to do
	 */
	public boolean isDone() {
		for (Call c : calls) {
			if (c.getState() != 2) {
				return false; // a call is left to be serviced or is in service
			}
		}
		return true;
	}
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public ArrayList<Call> getCalls() {
		return calls;
	}

	public void setCalls(ArrayList<Call> calls) {
		this.calls = calls;
	}

	public String toString() {
		String val = "Floor: " + floor + ", Direction: " + direction + ", Time: " + time + ", Calls: " + calls.toString();
		return val;
	}
}
