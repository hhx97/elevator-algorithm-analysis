import java.util.ArrayList;

/**
 * An interface for an elevator dispatch algorithm, i.e. an algo that decides which elevators get what calls
 */
public interface Algorithm {
	
	 // processes the calls by assigning them to the appropriate elevator; modifies elevators
	public void process(ArrayList<Elevator> elevators, ArrayList<Call> calls);
}
