
/**
 * Represents a call from a floor, i.e. someone pushing a button on a floor to summon the elevator
 */
public class Call {
	
	private int fromFloor; // the floor number whence the call originated
	private int toFloor; // the floor number the passenger will press once inside the elevator
	
	/**
	 * one of 0, 1, or 2 (enum):
	 * 0: call unserviced, no elevator has yet come to pick up the call
	 * 1: call is currently being serviced, i.e. the people got in the elevator and are currently in transit
	 * 2: call has been serviced, people are out of the elevator
	 */
	private int state;
	
	public Call (int fromFloor, int toFloor) {
		this.fromFloor = fromFloor;
		this.toFloor = toFloor;
		this.state = 0;
	}
	
	//the direction of the call (1 for up, -1 for down)
	public int direction() {
		return (toFloor - fromFloor) > 0 ? 1 : -1;
	}
	
	public int getFromFloor() {
		return fromFloor;
	}

	public void setFromFloor(int fromFloor) {
		if (fromFloor < 1 || fromFloor > Building.floors) {
			throw new IllegalArgumentException();
		}
		this.fromFloor = fromFloor;
	}

	public int getToFloor() {
		return toFloor;
	}

	public void setToFloor(int toFloor) {
		if (toFloor < 1 || toFloor > Building.floors) {
			throw new IllegalArgumentException();
		}
		this.toFloor = toFloor;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String toString() {
		return "" + fromFloor + " to " + toFloor + ", state " + state;
	}
}
