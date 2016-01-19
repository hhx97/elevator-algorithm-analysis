import java.util.*;

public class Building {
	
	private ArrayList<Elevator> elevators;
	private Algorithm algorithm;
	static final int floors = 21;
	static final int elevatorCount = 4;
	static final int callCount = 20;
	
	public Building (boolean isRandomData) {
		elevators = new ArrayList<Elevator>(elevatorCount);
		
		if (!isRandomData) {
			// not random data; have a bunch of arbitrary calls and elevator positions
			elevators.add(new Elevator(1, 1));
			elevators.add(new Elevator(1, -1));
			elevators.add(new Elevator(5, 1));
			elevators.add(new Elevator(10, -1));
		} else {
			for (int x = 0; x < elevatorCount; x++) {
				elevators.add(new Elevator((int)(Math.random() * floors) + 1, Math.random() < .5 ? -1 : 1));
			}
		}
	}
	
	/**
	 * Generates the calls for the sim in a random or fixed way
	 * @param isRandomData whether the calls should be randomly generated or not
	 * @return the calls
	 */
	ArrayList<Call> generateCalls (boolean isRandomData) {
		ArrayList<Call> calls = new ArrayList<Call>(callCount); // all the calls in the building at the start of the sim

		if (!isRandomData) {
			calls.add(new Call(1, 5));
			calls.add(new Call(2, 1));
			calls.add(new Call(4, 7));
			calls.add(new Call(4, 1));
			calls.add(new Call(6, 17));
			calls.add(new Call(8, 22));
			calls.add(new Call(14, 4));
			calls.add(new Call(21, 1));
			calls.add(new Call(22, 1));
		} else {
			for (int x = 0; x < callCount; x++) {
				calls.add(new Call((int)(Math.random() * floors) + 1, (int)(Math.random() * floors) + 1));
			}
		}
		
		return calls;
	}
	
	/**
	 * runs the simulation; this alters the elevators variable
	 * @param isRandomData : whether the calls should be random or not
	 */
	public int runSimulation(boolean isRandomData, Algorithm algorithm) {
		algorithm.process(elevators, generateCalls(isRandomData)); // assigns the calls to the appropriate elevator
		
		int counter = 0;
		while (!this.simulationIsDone(elevators)) {
			counter++;
			for (Elevator ele : elevators) {
				ele.step();
			}
		}
		
		return counter;
	}
	
	private boolean simulationIsDone(ArrayList<Elevator> elevators) {
		for (Elevator ele : elevators) {
			if (!ele.isDone()) {
				return false;
			}
		}
		return true;
	}
	
	public void outputSimulationResults() {
		for (Elevator e : elevators) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * @param stepsToCompletion, the counter of the number of steps it took
	 * return arraylist with first entry being the number of steps, second entry
	 * being the sum of all the times, third entry being the average of all the times, 
	 * and fourth entry being the maximum of all the times
	 */
	public ArrayList<Double> getSimulationResults(int stepsToCompletion) {
		double sum = 0;
		double max = 0;
		for (Elevator ele : elevators) {
			int time = ele.getTime();
			sum += time;
			if (time > max) {
				max = time;
			}
		}
		
		double avg = sum / elevators.size();
		
		ArrayList<Double> data = new ArrayList<Double>();
		data.add((double) stepsToCompletion);
		data.add(sum);
		data.add(avg);
		data.add(max);
		return data;
	}
	
	public static void main(String[] args) {
		boolean inputTypeRandom = true;
		
		ArrayList<ArrayList<Double>> results = new ArrayList<ArrayList<Double>>();
		
		for (int x = 0; x < 10000; x++) {
			Building building = new Building(inputTypeRandom);
			
			//-------- specify algorithm here -----------
			Algorithm algorithm = new Sectoring();
			//-------------------------------------------
			
			int stepsToCompletion = building.runSimulation(inputTypeRandom, algorithm);
			results.add(building.getSimulationResults(stepsToCompletion));
		}
		
		double avgSteps = 0;
		double avgSum = 0;
		double avgAvg = 0;
		double avgMax = 0;

		for (ArrayList<Double> a : results) {
			avgSteps += a.get(0);
			avgSum += a.get(1);
			avgAvg += a.get(2);
			avgMax += a.get(3);

			System.out.println(a);
		}
		
		avgSteps /= results.size();
		avgSum /= results.size();
		avgAvg /= results.size();
		avgMax /= results.size();
		
		ArrayList<Double> averages = new ArrayList<Double>();
		averages.add(avgSteps);
		averages.add(avgSum);
		averages.add(avgAvg);
		averages.add(avgMax);
		
		System.out.println("Averages:");
		System.out.println(averages);
	}	
}